package RentalReminder.dto;

public class SupabaseResponse {
    private SupabaseUser user;
    private SupabaseSession session;

    public SupabaseUser getUser() {
        return user;
    }

    public void setUser(SupabaseUser user) {
        this.user = user;
    }

    public SupabaseSession getSession() {
        return session;
    }

    public void setSession(SupabaseSession session) {
        this.session = session;
    }
}