package RentalReminder.repository;

import RentalReminder.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    // Find all currencies for a given user
    List<Currency> findByUserProfileSupabaseUserId(UUID supabaseUserId);

    // Find all currencies for a given user OR where userProfile is null
    List<Currency> findByUserProfileSupabaseUserIdOrUserProfileSupabaseUserIdIsNull(UUID supabaseUserId);
}
