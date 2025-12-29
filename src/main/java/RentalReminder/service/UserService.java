package RentalReminder.service;

import RentalReminder.client.SupabaseClient;
import RentalReminder.dto.request.LoginRequest;
import RentalReminder.dto.request.RegisterRequest;
import RentalReminder.dto.response.authentication.*;
import RentalReminder.dto.supabase.SupabaseResponse;
import RentalReminder.entity.UserProfile;
import RentalReminder.exception.ApiException;
import RentalReminder.mapper.UserProfileMapper;
import RentalReminder.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private SupabaseClient supabaseClient;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private AuthService authService;

    // Register user
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getPasswordConfirm())) {
            throw new ApiException("Passwords do not match", HttpStatus.BAD_REQUEST);
        }
        if (userProfileRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiException("Email already exists", HttpStatus.CONFLICT);
        }
        SupabaseResponse supabaseResponse = supabaseClient.register(registerRequest, SupabaseResponse.class);
        UUID supabaseUserId = UUID.fromString(supabaseResponse.getUser().getId());
        UserProfile userProfile = userProfileMapper.mapRegisterRequestToUserProfile(registerRequest);
        userProfile.setSupabaseUserId(supabaseUserId);
        userProfileRepository.save(userProfile);
        return createResponse("User registered successfully", RegisterResponse.class);
    }

    // Login user
    public LoginResponse loginUser(LoginRequest loginRequest) {
        if (!userProfileRepository.existsByEmail(loginRequest.getEmail())) {
            throw new ApiException("No user registered with that email", HttpStatus.NOT_FOUND);
        }
        SupabaseResponse supabaseResponse = supabaseClient.login(loginRequest, SupabaseResponse.class);
        String accessToken = supabaseResponse.getAccessToken();
        LoginResponse response = createResponse("Logged in successfully", LoginResponse.class);
        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .domain(".onrender.com")
                .path("/")
                .maxAge(60 * 60)
                .build();
        addCookie(response, cookie);
        return response;
    }

    // Logout user
    public LogoutResponse logoutUser() {
        ResponseCookie cookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .domain(".onrender.com")
                .path("/")
                .maxAge(0)
                .build();
        LogoutResponse response = createResponse("You have successfully logged out", LogoutResponse.class);
        addCookie(response, cookie);
        return response;
    }

    // Validate token
    public ValidateResponse validateToken(HttpServletRequest request) {
        UUID userId = getCurrentUserId(request, authService);
        return createResponse("Access token validated", ValidateResponse.class);
    }
}
