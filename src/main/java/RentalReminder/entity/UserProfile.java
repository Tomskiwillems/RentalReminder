package RentalReminder.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * UserProfile entity that pairs with Supabase Authentication.
 * 
 * This entity stores additional user profile information that extends beyond
 * what Supabase Auth provides. The supabaseUserId field references the UUID
 * from Supabase's auth.users table, creating a link between authentication
 * and your application's user data.
 * 
 * When a user registers via Supabase Auth, you can create a corresponding
 * UserProfile record using the user ID returned from the authentication response.
 */
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @Column(name = "supabase_user_id", nullable = false, unique = true)
    private UUID supabaseUserId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Currency> currencies = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile", fetch = FetchType.LAZY)
    private java.util.List<LentGood> lentGoods = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "userProfile", fetch = FetchType.LAZY)
    private java.util.List<BorrowedGood> borrowedGoods = new java.util.ArrayList<>();

    public UserProfile() {
    }

    public UserProfile(UUID supabaseUserId, String email) {
        this.supabaseUserId = supabaseUserId;
        this.email = email;
    }

    public UUID getSupabaseUserId() {
        return supabaseUserId;
    }

    public void setSupabaseUserId(UUID supabaseUserId) {
        this.supabaseUserId = supabaseUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<LentGood> getLentGoods() {
        return lentGoods;
    }

    public void setLentGoods(List<LentGood> lentGoods) {
        this.lentGoods = lentGoods;
    }

    public List<BorrowedGood> getBorrowedGoods() {
        return borrowedGoods;
    }
    public void setBorrowedGoods(List<BorrowedGood> borrowedGoods) {
        this.borrowedGoods = borrowedGoods;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

