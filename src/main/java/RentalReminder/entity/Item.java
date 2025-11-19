package RentalReminder.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_profile", nullable = false) // FK column
    private UserProfile userProfile;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private java.util.List<LentGood> lentGoods = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = name;
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
