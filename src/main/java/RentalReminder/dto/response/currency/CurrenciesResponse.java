package RentalReminder.dto.response.currency;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;

public class CurrenciesResponse extends BaseResponseDto {

    private List<CurrencyResponse> currencies;

    public List<CurrencyResponse> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<CurrencyResponse> currencies) {
        this.currencies = currencies;
    }
}