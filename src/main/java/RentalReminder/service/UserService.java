package RentalReminder.service;

import RentalReminder.client.SupabaseClient;
import RentalReminder.dto.RegisterRequestDto;
import RentalReminder.dto.SupabaseRegisterResponse;
import RentalReminder.entity.UserProfile;
import RentalReminder.entity.UserProfileMapper;
import RentalReminder.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private SupabaseClient supabaseClient;
    @Autowired
    private UserProfileMapper userProfileMapper;

    public String registerUser(RegisterRequestDto registerRequestDto) {
        if (userProfileRepository.existsByEmail(registerRequestDto.getEmail())) {
            return "Email already exists";
        }
        SupabaseRegisterResponse supabaseRegisterResponse = supabaseClient.register(registerRequestDto, SupabaseRegisterResponse.class);
        UserProfile userProfile = userProfileMapper.mapToUserProfile(registerRequestDto);
        return "User registered successfully";
    }

}
