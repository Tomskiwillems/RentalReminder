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
        List<Currency> currencies = safeRepo(
                () -> currencyRepository.findByUserProfileSupabaseUserId(currentUserId),
                "Failed to fetch currencies"
        );
        return currencyMapper.mapToCurrenciesResponse(currencies);
    }

    // Get a single currency
    public CurrencyResponse getCurrency(HttpServletRequest request, int currencyId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Currency currency = getEntityById(
                safeRepo(() -> currencyRepository.findById(currencyId), "Failed to fetch currency"),
                "Currency not found"
        );
        checkOwnership(currency.getUserProfile().getSupabaseUserId(), currentUserId,
                "Currency does not belong to this user");
        return currencyMapper.mapToCurrencyResponse(currency);
    }

    // Add a currency
    public CurrencyAddResponse addCurrency(HttpServletRequest request, CurrencyRequest dto) {
        UUID currentUserId = getCurrentUserId(request, authService);
        UserProfile userProfile = getEntityById(
                safeRepo(() -> userProfileRepository.findBySupabaseUserId(currentUserId), "Failed to fetch user profile"),
                "UserProfile not found"
        );
        Currency currency = currencyMapper.mapToCurrency(dto, userProfile);
        safeRepo(() -> currencyRepository.save(currency), "Failed to add currency");
        return createResponse("Currency added successfully", CurrencyAddResponse.class);
    }

    // Edit a currency
    public CurrencyEditResponse editCurrency(HttpServletRequest request, CurrencyRequest dto, int currencyId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Currency currency = getEntityById(
                safeRepo(() -> currencyRepository.findById(currencyId), "Failed to fetch currency"),
                "Currency not found"
        );
        checkOwnership(currency.getUserProfile().getSupabaseUserId(), currentUserId,
                "Currency does not belong to this user");
        currency.setName(dto.getName());
        currency.setDescription(dto.getDescription());
        safeRepo(() -> currencyRepository.save(currency), "Failed to edit currency");
        return createResponse("Currency edited successfully", CurrencyEditResponse.class);
    }

    // Delete a currency
    public CurrencyDeleteResponse deleteCurrency(HttpServletRequest request, int currencyId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Currency currency = getEntityById(
                safeRepo(() -> currencyRepository.findById(currencyId), "Failed to fetch currency"),
                "Currency not found"
        );
        checkOwnership(currency.getUserProfile().getSupabaseUserId(), currentUserId,
                "Currency does not belong to this user");
        safeRepoVoid(() -> currencyRepository.delete(currency), "Failed to delete currency");
        return createResponse("Currency deleted successfully", CurrencyDeleteResponse.class);
    }
}
