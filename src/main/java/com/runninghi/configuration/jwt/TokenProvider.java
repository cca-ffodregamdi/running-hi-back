package com.runninghi.configuration.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runninghi.member.command.domain.aggregate.entity.MemberRefreshToken;
import com.runninghi.member.query.infrastructure.repository.MemberQueryRefreshTokenRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@PropertySource("classpath:jwt.yml")
@Service
public class TokenProvider {
    private final String secretKey;
    private final long expirationMinutes;
    private final long refreshExpirationHours;
    private final String issuer;
    private final long reissueLimit;
    private final MemberQueryRefreshTokenRepository userQueryRefreshTokenRepository;

    // JWT 역직렬화를 위한 ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-minutes}") long expirationMinutes,
            @Value("${refresh-expiration-hours}") long refreshExpirationHours,
            @Value("${issuer}") String issuer,
            MemberQueryRefreshTokenRepository userQueryRefreshTokenRepository) {
        this.secretKey = secretKey;
        this.expirationMinutes = expirationMinutes;
        this.refreshExpirationHours = refreshExpirationHours;
        this.issuer = issuer;
        this.userQueryRefreshTokenRepository = userQueryRefreshTokenRepository;
        reissueLimit = refreshExpirationHours * 60 / expirationMinutes;
    }

    public String createAccessToken(String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(userSpecification)
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES)))
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS)))
                .compact();
    }

    @Transactional
    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        String subject = decodeJwtPayloadSubject(oldAccessToken);
        userQueryRefreshTokenRepository.findByUserIdAndReissueCountLessThan(UUID.fromString(subject.split(":")[0]), reissueLimit)
                .ifPresentOrElse(
                        MemberRefreshToken::increaseReissueCount,
                        () -> {
                            throw new ExpiredJwtException(null, null, "Refresh token expired.");
                        }
                );
        return createAccessToken(subject);
    }

    public String validateTokenAndGetSubject(String token) {
        return validateAndParseToken(token)
                .getBody()
                .getSubject();
    }

    private Jws<Claims> validateAndParseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token);
    }

    @Transactional(readOnly = true)
    public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String userId = decodeJwtPayloadSubject(oldAccessToken).split(":")[0];
        userQueryRefreshTokenRepository.findByUserIdAndReissueCountLessThan(UUID.fromString(userId), reissueLimit)
                .filter(userRefreshToken -> userRefreshToken.validateRefreshToken(refreshToken))
                .orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh token expired."));
    }

    private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();
    }


}
