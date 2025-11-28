package RentalReminder.dto.response.currency;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;
import java.util.Map;

public class CurrenciesResponse extends BaseResponseDto {

    private List<Map<String, Object>> currencies;

    public List<Map<String, Object>> getCurrencies() {
        return currencies;
    }
    public void setCurrencies(List<Map<String, Object>> currencies) {
        this.currencies = currencies;
    }
}
