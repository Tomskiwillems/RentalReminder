package RentalReminder.repository;

import RentalReminder.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    // Find all items for a given user
    List<Item> findByUserProfileSupabaseUserId(UUID supabaseUserId);
}
