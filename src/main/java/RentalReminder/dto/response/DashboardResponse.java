package RentalReminder.dto.response;

import RentalReminder.dto.response.borrowedgood.BorrowedGoodsResponse;
import RentalReminder.dto.response.lentgood.LentGoodsResponse;

public class DashboardResponse extends BaseResponseDto {

    private BorrowedGoodsResponse borrowedGoodsResponse = new BorrowedGoodsResponse();
    private LentGoodsResponse lentGoodsResponse = new LentGoodsResponse();

    public BorrowedGoodsResponse getBorrowedGoodsResponse() {
        return borrowedGoodsResponse;
    }

    public void setBorrowedGoodsResponse(BorrowedGoodsResponse borrowedGoodsResponse) {
        this.borrowedGoodsResponse = borrowedGoodsResponse;
    }

    public LentGoodsResponse getLentGoodsResponse() {
        return lentGoodsResponse;
    }

    public void setLentGoodsResponse(LentGoodsResponse lentGoodsResponse) {
        this.lentGoodsResponse = lentGoodsResponse;
    }
}
