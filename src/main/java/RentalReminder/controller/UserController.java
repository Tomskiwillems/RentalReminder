package RentalReminder.controller;

import RentalReminder.dto.request.LoginRequest;
import RentalReminder.dto.request.RegisterRequest;
import RentalReminder.dto.response.authentication.*;
import RentalReminder.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> logInUser(@RequestBody LoginRequest loginRequest) {
        return handle(LoginResponse::new, response -> userService.loginUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return handle(RegisterResponse::new, response -> userService.registerUser(registerRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logOutUser() {
        return handle(LogoutResponse::new, response -> userService.logoutUser());
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateResponse> validateToken(HttpServletRequest request) {
        return handle(ValidateResponse::new, response -> userService.validateToken(request));
    }
}
