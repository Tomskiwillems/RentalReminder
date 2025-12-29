package RentalReminder.repository;

import RentalReminder.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    // Find all contacts for a given user
    List<Contact> findByUserProfileSupabaseUserId(UUID supabaseUserId);
}
