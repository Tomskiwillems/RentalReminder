package RentalReminder.service;

import RentalReminder.client.SupabaseClient;
import RentalReminder.dto.LoginRequestDto;
import RentalReminder.dto.RegisterRequestDto;
import RentalReminder.dto.SupabaseResponse;
import RentalReminder.entity.UserProfile;
import RentalReminder.mapper.UserProfileMapper;
import RentalReminder.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private SupabaseClient supabaseClient;
    @Autowired
    private UserProfileMapper userProfileMapper;

    public String registerUser(RegisterRequestDto registerRequestDto) {
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getPasswordConfirm())) {
            return "Passwords do not match";
        }
        if (userProfileRepository.existsByEmail(registerRequestDto.getEmail())) {
            return "Email already exists";
        }
        SupabaseResponse supabaseResponse = supabaseClient.register(registerRequestDto, SupabaseResponse.class);
        UserProfile userProfile = userProfileMapper.mapRegisterRequestDtoToUserProfile(registerRequestDto);
        UUID uuid = UUID.fromString(supabaseResponse.getUser().getId());
        userProfile.setSupabaseUserId(uuid);
        userProfileRepository.save(userProfile);
        return "User registered successfully";
    }

    public String loginUser(LoginRequestDto loginRequestDto) {
        if (!userProfileRepository.existsByEmail(loginRequestDto.getEmail())) {
            return "No user registered with that email";
        }
        SupabaseResponse supabaseResponse = supabaseClient.login(loginRequestDto, SupabaseResponse.class);
        UserProfile userProfile = userProfileMapper.mapLoginRequestDtoToUserProfile(loginRequestDto);
        UUID uuid = UUID.fromString(supabaseResponse.getUser().getId());
        if (!userProfileRepository.existsBySupabaseUserId(uuid)) {
            return "Wrong password";
        }
        return "You are logged in successfully";
    }
}
