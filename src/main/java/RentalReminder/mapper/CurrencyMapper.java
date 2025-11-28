package RentalReminder.mapper;

import RentalReminder.dto.request.CurrencyRequest;
import RentalReminder.dto.response.currency.CurrencyResponse;
import RentalReminder.dto.response.currency.CurrenciesResponse;
import RentalReminder.entity.Currency;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyMapper {

    public CurrenciesResponse mapToCurrenciesResponse(List<Currency> currencies) {
        CurrenciesResponse response = new CurrenciesResponse();
        response.setCurrencies(currencies.stream()
                .map(c -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", c.getId());
                    map.put("name", c.getName());
                    map.put("description", c.getDescription());
                    return map;
                })
                .toList());
        return response;
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
