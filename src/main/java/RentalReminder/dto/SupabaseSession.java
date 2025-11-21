package RentalReminder.dto;

public class SupabaseSession {
    private String access_token;
    private String token_type;
    private int expires_in;
    private long expires_at;
    private String refresh_token;
    private String provider_token;
    private String provider_refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public long getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(long expires_at) {
        this.expires_at = expires_at;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getProvider_token() {
        return provider_token;
    }

    public void setProvider_token(String provider_token) {
        this.provider_token = provider_token;
    }

    public String getProvider_refresh_token() {
        return provider_refresh_token;
    }

    public void setProvider_refresh_token(String provider_refresh_token) {
        this.provider_refresh_token = provider_refresh_token;
    }
}