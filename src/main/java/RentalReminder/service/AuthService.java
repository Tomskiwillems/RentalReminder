package RentalReminder.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${SUPABASE_JWT_SECRET}")
    private String SUPABASE_JWT_SECRET;

    public UUID verifyTokenAndGetUserId(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token missing");
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Base64.getDecoder().decode(SUPABASE_JWT_SECRET))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired token", e);
        }
    }
}