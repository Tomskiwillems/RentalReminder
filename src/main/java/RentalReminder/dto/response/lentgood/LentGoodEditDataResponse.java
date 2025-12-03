package RentalReminder.dto.response.lentgood;

public class LentGoodEditDataResponse extends LentGoodAddDataResponse {

    private LentGoodResponse lentGood;

    public LentGoodResponse getLentGood() {
        return lentGood;
    }

    public void setLentGood(LentGoodResponse lentGood) {
        this.lentGood = lentGood;
    }
}