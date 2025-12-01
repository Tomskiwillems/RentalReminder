package RentalReminder.dto.response.borrowedgood;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;
import java.util.Map;

public class BorrowedGoodsResponse extends BaseResponseDto {

    private List<Map<String, Object>> borrowedGoods;

    public List<Map<String, Object>> getBorrowedGoods() {
        return borrowedGoods;
    }

    public void setBorrowedGoods(List<Map<String, Object>> borrowedGoods) {
        this.borrowedGoods = borrowedGoods;
    }
}
