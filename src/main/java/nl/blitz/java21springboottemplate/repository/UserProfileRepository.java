package nl.blitz.java21springboottemplate.repository;

import nl.blitz.java21springboottemplate.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for UserProfile entity.
 * 
 * This repository provides data access methods for user profiles that are
 * linked to Supabase authentication. Use the findBySupabaseUserId method
 * to retrieve a user profile after authenticating with Supabase, using the
 * user ID from the authentication token or response.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    /**
     * Find a user profile by Supabase user ID.
     * This is the primary method to link authenticated Supabase users
     * to their profile data in your application.
     * 
     * @param supabaseUserId The UUID from Supabase's auth.users table
     * @return Optional containing the UserProfile if found
     */
    Optional<UserProfile> findBySupabaseUserId(UUID supabaseUserId);

    /**
     * Find a user profile by email address.
     * 
     * @param email The email address
     * @return Optional containing the UserProfile if found
     */
    Optional<UserProfile> findByEmail(String email);

    /**
     * Check if a user profile exists for the given Supabase user ID.
     * 
     * @param supabaseUserId The UUID from Supabase's auth.users table
     * @return true if a profile exists, false otherwise
     */
    boolean existsBySupabaseUserId(UUID supabaseUserId);
}

