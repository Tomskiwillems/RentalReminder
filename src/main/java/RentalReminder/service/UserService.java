package RentalReminder.service;

import RentalReminder.client.SupabaseClient;
import RentalReminder.dto.authentication.LoginRequest;
import RentalReminder.dto.authentication.RegisterRequest;
import RentalReminder.dto.authentication.SupabaseResponse;
import RentalReminder.entity.UserProfile;
import RentalReminder.mapper.UserProfileMapper;
import RentalReminder.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private SupabaseClient supabaseClient;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private AuthService authService;

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
        return ResponseCookie.from("access_token", access_token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(60 * 60)
                .build();
    }

    public String validateToken(HttpServletRequest request) {
        String accessToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("access_token"))
                .findFirst()
                .map(jakarta.servlet.http.Cookie::getValue)
                .orElse(null);
        UUID userId = authService.verifyTokenAndGetUserId(accessToken);
        if (userId == null) {
            throw new RuntimeException("Invalid token");
        }
        return "Access token validated";
    }
}
