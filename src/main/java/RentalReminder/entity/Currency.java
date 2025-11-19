package RentalReminder.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_profile") // FK column
    private UserProfile userProfile;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
    private java.util.List<LentGood> lentGoods = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY)
    private java.util.List<BorrowedGood> borrowedGoods = new java.util.ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
