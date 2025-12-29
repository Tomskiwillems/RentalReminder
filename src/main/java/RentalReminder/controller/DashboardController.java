package RentalReminder.controller;

import RentalReminder.dto.response.DashboardResponse;
import RentalReminder.service.DashboardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController extends BaseController{

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardData(HttpServletRequest request) {

        return handle(
                DashboardResponse::new,
                response -> dashboardService.getDashboardData(request)
        );
    }

}
