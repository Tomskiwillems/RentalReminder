package RentalReminder.dto.response.borrowedgood;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;

public class BorrowedGoodsResponse extends BaseResponseDto {

    private List<BorrowedGoodResponse> borrowedGoods;

    public List<BorrowedGoodResponse> getBorrowedGoods() {
        return borrowedGoods;
    }

    public void setBorrowedGoods(List<BorrowedGoodResponse> borrowedGoods) {
        this.borrowedGoods = borrowedGoods;
    }
}
