package RentalReminder.controller;

import RentalReminder.dto.LoginRequest;
import RentalReminder.dto.RegisterRequest;
import RentalReminder.dto.RegisterResponse;
import RentalReminder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> logInUser(@RequestBody LoginRequest loginRequest) {
        final String message = userService.loginUser(loginRequest);
        return ResponseEntity.ok(message);
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

    @GetMapping("/logout")
    public String logOutUser() {
        return "You have succesfully logged out";
    }
}
