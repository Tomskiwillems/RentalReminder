package RentalReminder.service;

import RentalReminder.entity.BorrowedGood;
import RentalReminder.entity.LentGood;
import RentalReminder.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RentalReminderService {

    @Autowired
    private LentGoodRepository lentGoodRepository;
    @Autowired
    private BorrowedGoodRepository borrowedGoodRepository;
    @Autowired
    private AuthService authService;

    public Map<String, Object> getDashboardData(HttpServletRequest request) {
        String accessToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("access_token"))
                .findFirst()
                .map(jakarta.servlet.http.Cookie::getValue)
                .orElse(null);
        if (accessToken == null) {
            throw new RuntimeException("Access token is null");
        }
        UUID userProfileId = authService.verifyTokenAndGetUserId(accessToken);
        List<LentGood> lentGoods = lentGoodRepository.findByUserProfileSupabaseUserId(userProfileId);
        List<BorrowedGood> borrowedGoods = borrowedGoodRepository.findByUserProfileSupabaseUserId(userProfileId);
        Map<String, Object> response = new HashMap<>();
        response.put("borrowedGoods", borrowedGoods);
        response.put("lentGoods", lentGoods);
        return response;
    }
}
