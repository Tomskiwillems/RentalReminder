package RentalReminder.dto.response.borrowedgood;

public class BorrowedGoodEditDataResponse extends BorrowedGoodAddDataResponse {

    private BorrowedGoodResponse borrowedGood;

    public BorrowedGoodResponse getBorrowedGood() {
        return borrowedGood;
    }

    public void setBorrowedGood(BorrowedGoodResponse borrowedGood) {
        this.borrowedGood = borrowedGood;
    }
}