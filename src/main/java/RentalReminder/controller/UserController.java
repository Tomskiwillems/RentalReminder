package RentalReminder.controller;

import RentalReminder.dto.LoginRequestDto;
import RentalReminder.dto.RegisterRequestDto;
import RentalReminder.repository.UserProfileRepository;
import RentalReminder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String logInUser(LoginRequestDto loginRequestDto) {
        return "You have succesfully logged in";
    }

    @PostMapping("/register")
    public String registerUser(RegisterRequestDto registerRequestDto) {
        return "You have succesfully registered";
    }

    @GetMapping("/logout")
    public String logOutUser() {
        return "You have succesfully logged out";
    }
}
