package RentalReminder.mapper;

import RentalReminder.dto.LoginRequestDto;
import RentalReminder.dto.RegisterRequestDto;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserProfileMapper {

    public UserProfile mapRegisterRequestDtoToUserProfile(RegisterRequestDto registerRequestDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setSupabaseUserId(registerRequestDto.getId());
        userProfile.setEmail(registerRequestDto.getEmail());
        return userProfile;
    }

    public UserProfile mapLoginRequestDtoToUserProfile(LoginRequestDto loginRequestDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setSupabaseUserId(loginRequestDto.getId());
        userProfile.setEmail(loginRequestDto.getEmail());
        return userProfile;
    }
}
