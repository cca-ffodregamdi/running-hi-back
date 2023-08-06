package com.runninghi.configuration.jwt;

import com.runninghi.user.command.application.service.UserService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserService userService;
    private final PrincipalDetailsService principalDetailsService;
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager
            , UserService userService
            , PrincipalDetailsService principalDetailsService) {

        super(authenticationManager);
        this.userService = userService;
        this.principalDetailsService = principalDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            String tokenHeader = request.getHeader("Authorization");
            String jwtToken = null;

            if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith("Bearer")) {
                jwtToken = tokenHeader.replace("Bearer ", "");
            }

            if (jwtToken != null && isValid(jwtToken)) {
                SecurityContextHolder.getContext().setAuthentication(getAuth(jwtToken));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuth(String jwtToken) {
        PrincipalDetails user = (PrincipalDetails) principalDetailsService.loadUserByUsername(getEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    private String getEmail(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(jwtToken).getBody()
                .getSubject();
    }

    private boolean isValid(String jwtToken) {
        boolean ret = true;

        Jws<Claims> jws = null;

        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);

            if (jws == null ||
                    jws.getBody().getSubject() == null ||
                    jws.getBody().getExpiration().before(new Date())) {
                ret = false;
            }

        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    private Key getSecretKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
