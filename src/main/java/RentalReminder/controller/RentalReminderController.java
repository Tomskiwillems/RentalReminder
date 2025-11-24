package RentalReminder.controller;

import RentalReminder.dto.DashboardResponse;
import RentalReminder.service.RentalReminderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RentalReminderController {

    @Autowired
    private RentalReminderService rentalReminderService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardData(HttpServletRequest request) {
        DashboardResponse dashboardResponse = new DashboardResponse();
        try {
            dashboardResponse.setGridViewItems(rentalReminderService.getDashboardData(request));
            return ResponseEntity.ok(dashboardResponse);
        }
        catch (RuntimeException e) {
            dashboardResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(dashboardResponse);
        }
    }

    @PostMapping("/add-contact")
    public String addContact() {
        return "You have successfully added a contact";
    }

    @PostMapping("/add-item")
    public String addItem() {
        return "You have successfully added an item";
    }

    @GetMapping("/add-lent-item")
    public String showAddLentItem() {
        return "Welcome to the page where you can add a lent item";
    }

    @PostMapping("/add-lent-item")
    public String addLentItem() {
        return "You have successfully added a lent item";
    }

    @GetMapping("/add-borrowed-item")
    public String showAddBorrowedItem() {
        return "Welcome to the page where you can add a borrowed item";
    }

    @PostMapping("/add-borrowed-item")
    public String addBorrowedItem() {
        return "You have successfully added a borrowed item";
    }

    @GetMapping("/lent-item")
    public String showLentItem() {
        return "Welcome to the page where you can see your lent items";
    }

    @GetMapping("/borrowed-item")
    public String showBorrowedItem() {
        return "Welcome to the page where you can see your borrowed items";
    }

    @PostMapping("/lent-item/edit")
    public String editLentItem() {
        return "You have succesfully edited a lent item";
    }

    @PostMapping("/lent-item/delete")
    public String deleteLentItem() {
        return "You have succesfully deleted a lent item";
    }

    @PostMapping("/borrowed-item/edit")
    public String borrowedLentItem() {
        return "You have succesfully edited a borrowed item";
    }

    @PostMapping("/borrowed-item/delete")
    public String deleteBorrowedItem() {
        return "You have succesfully deleted a borrowed item";
    }
}
