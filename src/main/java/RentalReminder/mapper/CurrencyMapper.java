package RentalReminder.mapper;

import RentalReminder.dto.request.CurrencyRequest;
import RentalReminder.dto.response.currency.CurrencyResponse;
import RentalReminder.dto.response.currency.CurrenciesResponse;
import RentalReminder.entity.Currency;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyMapper {

    public CurrenciesResponse mapToCurrenciesResponse(List<Currency> currencies) {
        CurrenciesResponse response = new CurrenciesResponse();
        List<CurrencyResponse> currencyResponses = currencies.stream()
                .map(this::mapToCurrencyResponse)
                .collect(Collectors.toList());
        response.setCurrencies(currencyResponses);
        return response;
    }

    public List<CurrencyResponse> mapToCurrencyResponseList(List<Currency> currencies) {
        return currencies.stream()
                .map(this::mapToCurrencyResponse)
                .collect(Collectors.toList());
    }

    public CurrencyResponse mapToCurrencyResponse(Currency currency) {
        CurrencyResponse response = new CurrencyResponse();
        response.setId(currency.getId());
        response.setName(currency.getName());
        response.setDescription(currency.getDescription());
        return response;
    }

    public Currency mapToCurrency(CurrencyRequest dto, UserProfile userProfile) {
        Currency currency = new Currency();
        currency.setName(dto.getName());
        currency.setDescription(dto.getDescription());
        currency.setUserProfile(userProfile);
        return currency;
    }
}
