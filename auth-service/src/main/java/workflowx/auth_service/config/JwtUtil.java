package workflowx.auth_service.config;

import io.jsonwebtoken.Jwts;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


@Component
public class JwtUtil {
    private final String SECRET_KEY = "your-256-bit-secret-key-generated-securely";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 86400000))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    /**
     * get the username from the token
     * @param token, the token itself
     * @return
     */
    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * check if the token has expired
     * @param token, the token itself
     * @return
     */
    public boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody().getExpiration().before(new java.util.Date());
    }

    public boolean validateToken(String token, String username) {
        return !isTokenExpired(token) && username.equals(extractEmail(token));
    }
}
