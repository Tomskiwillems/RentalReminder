package RentalReminder.service;

import com.auth0.jwk.Jwk;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final SecretKey secretKey;

    public AuthService(
            @Value("${SUPABASE_JWT_SECRET}") String jwtSecret
    ) {
        byte[] keybytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keybytes);
    }


    public UUID verifyTokenAndGetUserId(String token) {
        Map<String, Object> claims = validate(token);
        String userId = (String) claims.get("sub");
        if (userId == null) {
            throw new RuntimeException("Token missing 'sub' claim");
        }
        return UUID.fromString(userId);
    }

    public Map<String, Object> validate(String jwt) {
        try {
            Jws<Claims> parsed = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt);
            return parsed.getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid Supabase JWT: " + e.getMessage());
        }
    }
}