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

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OffsetDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(OffsetDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public OffsetDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(OffsetDateTime created_at) {
        this.created_at = created_at;
    }

    public Map<String, Object> getUser_metadata() {
        return user_metadata;
    }

    public void setUser_metadata(Map<String, Object> user_metadata) {
        this.user_metadata = user_metadata;
    }

    public Map<String, Object> getApp_metadata() {
        return app_metadata;
    }

    public void setApp_metadata(Map<String, Object> app_metadata) {
        this.app_metadata = app_metadata;
    }

    public OffsetDateTime getConfirmation_sent_at() {
        return confirmation_sent_at;
    }

    public void setConfirmation_sent_at(OffsetDateTime confirmation_sent_at) {
        this.confirmation_sent_at = confirmation_sent_at;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OffsetDateTime getEmail_confirmed_at() {
        return email_confirmed_at;
    }

    public void setEmail_confirmed_at(OffsetDateTime email_confirmed_at) {
        this.email_confirmed_at = email_confirmed_at;
    }
}