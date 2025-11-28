package RentalReminder.service;

import RentalReminder.entity.BorrowedGood;
import RentalReminder.entity.LentGood;
import RentalReminder.mapper.GridViewMapper;
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
    @Autowired
    private GridViewMapper rentalReminderGridViewGoodsMapper;

    public Map<String, Object> getDashboardData(HttpServletRequest request) {
        String accessToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("access_token"))
                .findFirst()
                .map(jakarta.servlet.http.Cookie::getValue)
                .orElse(null);
        UUID userProfileId = authService.verifyTokenAndGetUserId(accessToken);
        List<LentGood> lentGoods = lentGoodRepository.findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(userProfileId);
        List<BorrowedGood> borrowedGoods = borrowedGoodRepository.findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(userProfileId);
        return rentalReminderGridViewGoodsMapper.mapLentAndBorrowedGoodsToMap(lentGoods, borrowedGoods);
    }
}
