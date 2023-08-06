package com.runninghi.configuration.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final long VALID_TIME = 1000L * 60 * 60; // 1시간
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public JwtAuthenticationFilter(UserService userService) {
        this.userService = userService;

        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("--JWT AUTHENTICATION FILTER--");

        try {

            UserLoginVO creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginVO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            null
                    ));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String email = ((PrincipalDetails) authResult.getPrincipal()).getUsername();
        UserResponse userResponse = userService.findByEmail(email);

        String jwtToken = Jwts.builder()
                .setSubject(userResponse.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + VALID_TIME))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        response.addHeader("token", jwtToken);
        response.addHeader("username", userResponse.getUsername());
    }

    private Key getSecretKey() {
        byte[] KeyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(KeyBytes);
    }

}
