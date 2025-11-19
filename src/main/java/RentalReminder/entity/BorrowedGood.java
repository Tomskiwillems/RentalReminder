package RentalReminder.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "borrowed_good")
@org.hibernate.annotations.Check(constraints = "(id_item IS NULL) <> (id_currency IS NULL)")
public class BorrowedGood {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_profile", nullable = false)
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contact", nullable = false)
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_currency")
    private Currency currency;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    // -------- getters & setters --------

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }

    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}