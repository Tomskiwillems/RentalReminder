package RentalReminder.controller;

import RentalReminder.dto.LoginRequestDto;
import RentalReminder.dto.RegisterRequestDto;
import RentalReminder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String logInUser(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.loginUser(loginRequestDto);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        return userService.registerUser(registerRequestDto);
    }

    @GetMapping("/logout")
    public String logOutUser() {
        return "You have succesfully logged out";
    }
}
