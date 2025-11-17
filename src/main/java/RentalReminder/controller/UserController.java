package RentalReminder.controller;

import RentalReminder.repository.UserProfileRepository;
import RentalReminder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String logInUser() {
        return "You have succesfully logged in";
    }

    @PostMapping("/signup")
    public String signUpUser() {
        return "You have succesfully signed up";
    }

    @GetMapping("/logout")
    public String logOutUser() {
        return "You have succesfully logged out";
    }
}
