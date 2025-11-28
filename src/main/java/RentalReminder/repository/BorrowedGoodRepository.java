package RentalReminder.repository;

import RentalReminder.entity.BorrowedGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowedGoodRepository extends JpaRepository<BorrowedGood, Integer> {

    // Find all items for a given user
    List<BorrowedGood> findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(UUID supabaseUserId);
}
