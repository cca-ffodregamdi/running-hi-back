package com.runninghi.configuration.jwt;

public class JwtTokenProvider {

    private final long VALID_MILISECOND = 1000L * 60 * 60; // 1 시간
    private final PrincipalDetailsService principalDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSecretKey(String secretKey) {
        byte[] KeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

    private String getUsername(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey(secretKey))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String jwtToken) {
        try {
            log.info("validate..");
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey(secretKey))
                    .build()
                    .parseClaimsJws(jwtToken);
            log.info("{}", claims.getBody().getExpiration());
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(getUsername(jwtToken));
        log.info("PASSWORD : {}", userDetails.getPassword());
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }


    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + VALID_MILISECOND))
                .signWith(getSecretKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }
}
