package RentalReminder.controller;

import RentalReminder.dto.response.DashboardResponse;
import RentalReminder.service.RentalReminderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {

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
}
