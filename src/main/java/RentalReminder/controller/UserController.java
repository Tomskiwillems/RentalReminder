package RentalReminder.controller;

import RentalReminder.dto.authentication.*;
import RentalReminder.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> logInUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            ResponseCookie responseCookie = userService.loginUser(loginRequest);
            loginResponse.setMessage("Logged in successfully");
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(loginResponse);
        }
        catch (RuntimeException e){
            loginResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(loginResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            registerResponse.setMessage(userService.registerUser(registerRequest));
            return ResponseEntity.ok(registerResponse);
        }
        catch (RuntimeException e){
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logOutUser() {
        ResponseCookie responseCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
        LogoutResponse response = new LogoutResponse();
        response.setMessage("You have successfully logged out");
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateResponse> validateToken(HttpServletRequest request) {
        ValidateResponse validateResponse = new ValidateResponse();
        try {
            validateResponse.setMessage(userService.validateToken(request));
            return ResponseEntity.ok().body(validateResponse);
        }
        catch (RuntimeException e){
            validateResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validateResponse);
        }
    }
}
