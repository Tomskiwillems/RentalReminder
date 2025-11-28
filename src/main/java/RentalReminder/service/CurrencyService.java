package RentalReminder.service;

import RentalReminder.dto.request.CurrencyRequest;
import RentalReminder.dto.response.currency.*;
import RentalReminder.entity.Currency;
import RentalReminder.entity.UserProfile;
import RentalReminder.mapper.CurrencyMapper;
import RentalReminder.repository.CurrencyRepository;
import RentalReminder.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CurrencyService extends BaseService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private CurrencyMapper currencyMapper;

    // Get all currencies for the current user
    public CurrenciesResponse getCurrencies(HttpServletRequest request) {
        UUID currentUserId = getCurrentUserId(request, authService);
        List<Currency> currencies = currencyRepository.findByUserProfileSupabaseUserId(currentUserId);
        return currencyMapper.mapToCurrenciesResponse(currencies);
    }

    // Get a single currency
    public CurrencyResponse getCurrency(HttpServletRequest request, int currencyId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Currency currency = getEntityById(currencyRepository.findById(currencyId), "Currency not found");
        checkOwnership(currency.getUserProfile().getSupabaseUserId(), currentUserId, "Currency does not belong to this user");
        return currencyMapper.mapToCurrencyResponse(currency);
    }

    // Add a currency
    public CurrencyAddResponse addCurrency(HttpServletRequest request, CurrencyRequest dto) {
        UUID currentUserId = getCurrentUserId(request, authService);
        UserProfile userProfile = getEntityById(
                userProfileRepository.findBySupabaseUserId(currentUserId),
                "UserProfile not found"
        );
        Currency currency = currencyMapper.mapToCurrency(dto, userProfile);
        Currency savedCurrency = currencyRepository.save(currency);
        if (savedCurrency.getId() < 1) {
            throw new RuntimeException("Failed to add the currency");
        }
        return createResponse("Currency added successfully", CurrencyAddResponse.class);
    }

    // Edit a currency
    public CurrencyEditResponse editCurrency(HttpServletRequest request, CurrencyRequest dto, int currencyId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Currency currency = getEntityById(currencyRepository.findById(currencyId), "Currency not found");
        checkOwnership(currency.getUserProfile().getSupabaseUserId(), currentUserId, "Currency does not belong to this user");

        currency.setName(dto.getName());
        currency.setDescription(dto.getDescription());

        Currency updatedCurrency = currencyRepository.save(currency);

        if (!updatedCurrency.getName().equals(dto.getName()) ||
                !updatedCurrency.getDescription().equals(dto.getDescription())) {
            throw new RuntimeException("Failed to edit the currency");
        }

        return createResponse("Currency edited successfully", CurrencyEditResponse.class);
    }

    // Delete a currency
    public CurrencyDeleteResponse deleteCurrency(HttpServletRequest request, int currencyId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Currency currency = getEntityById(currencyRepository.findById(currencyId), "Currency not found");
        checkOwnership(currency.getUserProfile().getSupabaseUserId(), currentUserId, "Currency does not belong to this user");

        currencyRepository.delete(currency);

        if (currencyRepository.existsById(currencyId)) {
            throw new RuntimeException("Failed to delete the currency");
        }

        return createResponse("Currency deleted successfully", CurrencyDeleteResponse.class);
    }
}
