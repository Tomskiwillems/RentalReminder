package RentalReminder.service;

import RentalReminder.client.SupabaseClient;
import RentalReminder.dto.LoginRequest;
import RentalReminder.dto.RegisterRequest;
import RentalReminder.dto.SupabaseResponse;
import RentalReminder.entity.UserProfile;
import RentalReminder.mapper.UserProfileMapper;
import RentalReminder.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
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

    @Value("${cookie.secure:true}")
    private boolean cookieSecure;

    public String registerUser(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getPasswordConfirm())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (userProfileRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        SupabaseResponse supabaseResponse = supabaseClient.register(registerRequest, SupabaseResponse.class);
        UserProfile userProfile = userProfileMapper.mapRegisterRequestToUserProfile(registerRequest);
        UUID uuid = UUID.fromString(supabaseResponse.getUser().getId());
        userProfile.setSupabaseUserId(uuid);
        userProfileRepository.save(userProfile);
        return "User registered successfully";
    }

    public ResponseCookie loginUser(LoginRequest loginRequest) {
        if (!userProfileRepository.existsByEmail(loginRequest.getEmail())) {
            throw new RuntimeException("No user registered with that email");
        }
        SupabaseResponse supabaseResponse = supabaseClient.login(loginRequest, SupabaseResponse.class);
        String access_token = supabaseResponse.getAccessToken();
        // Note: SameSite=None requires secure=true per browser spec
        // Modern browsers allow secure=true cookies on localhost even with HTTP
        return ResponseCookie.from("access_token", access_token)
                .httpOnly(true)
                .secure(cookieSecure)  // true for cross-origin (browsers allow this on localhost HTTP)
                .sameSite("None")  // Required for cross-origin requests (different ports = different origins)
                .path("/")
                .maxAge(60 * 60)
                .build();
    }
}
