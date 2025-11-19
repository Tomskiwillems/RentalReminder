package RentalReminder.entity;

import RentalReminder.dto.RegisterRequestDto;
import org.springframework.stereotype.Service;

@Service
public class UserProfileMapper {

    public UserProfile mapToUserProfile(RegisterRequestDto registerRequestDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setSupabaseUserId(registerRequestDto.getId());
        userProfile.setEmail(registerRequestDto.getEmail());
        return userProfile;
    }
}
