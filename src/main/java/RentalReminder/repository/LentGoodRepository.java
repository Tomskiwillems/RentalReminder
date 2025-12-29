package RentalReminder.repository;

import RentalReminder.entity.LentGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LentGoodRepository extends JpaRepository<LentGood, Integer> {

    // Find all items for a given user
    List<LentGood> findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(UUID supabaseUserId);
}
