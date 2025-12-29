package RentalReminder.service;

import RentalReminder.dto.response.DashboardResponse;
import RentalReminder.entity.BorrowedGood;
import RentalReminder.entity.LentGood;
import RentalReminder.mapper.BorrowedGoodMapper;
import RentalReminder.mapper.LentGoodMapper;
import RentalReminder.repository.BorrowedGoodRepository;
import RentalReminder.repository.LentGoodRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DashboardService extends BaseService {

    @Autowired
    private LentGoodRepository lentGoodRepository;
    @Autowired
    private BorrowedGoodRepository borrowedGoodRepository;
    @Autowired
    private LentGoodMapper lentGoodMapper;
    @Autowired
    private BorrowedGoodMapper borrowedGoodMapper;
    @Autowired
    private AuthService authService;

    public DashboardResponse getDashboardData(HttpServletRequest request) {

        UUID userId = getCurrentUserId(request, authService);
        List<LentGood> lentGoods = safeRepo(
                () -> lentGoodRepository
                        .findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(userId),
                "Failed to fetch lent goods"
        );
        List<BorrowedGood> borrowedGoods = safeRepo(
                () -> borrowedGoodRepository
                        .findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(userId),
                "Failed to fetch borrowed goods"
        );
        DashboardResponse response = new DashboardResponse();
        response.setLentGoodsResponse(lentGoodMapper.mapToLentGoodsResponse(lentGoods));
        response.setBorrowedGoodsResponse(borrowedGoodMapper.mapToBorrowedGoodsResponse(borrowedGoods));
        return response;
    }
}
