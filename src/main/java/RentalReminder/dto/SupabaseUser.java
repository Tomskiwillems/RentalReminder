package RentalReminder.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public class SupabaseUser {
    private String id;
    private String aud;
    private String role;
    private String email;

    private OffsetDateTime email_confirmed_at;
    private String phone;
    private OffsetDateTime confirmation_sent_at;
    private Map<String, Object> app_metadata;
    private Map<String, Object> user_metadata;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}