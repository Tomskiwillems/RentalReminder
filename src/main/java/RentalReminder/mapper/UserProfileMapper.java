package RentalReminder.mapper;

import RentalReminder.dto.authentication.RegisterRequest;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserProfileMapper {

    public UserProfile mapRegisterRequestToUserProfile(RegisterRequest registerRequest) {
        UserProfile userProfile = new UserProfile();
        userProfile.setSupabaseUserId(registerRequest.getId());
        userProfile.setEmail(registerRequest.getEmail());
        return userProfile;
    }
}
