package RentalReminder.service;

import RentalReminder.dto.request.LentGoodRequest;
import RentalReminder.dto.response.lentgood.*;
import RentalReminder.entity.*;
import RentalReminder.mapper.LentGoodMapper;
import RentalReminder.mapper.ContactMapper;
import RentalReminder.mapper.CurrencyMapper;
import RentalReminder.mapper.ItemMapper;
import RentalReminder.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LentGoodService extends BaseGoodService<
        LentGood,
        LentGoodAddResponse,
        LentGoodEditResponse,
        LentGoodAddDataResponse,
        LentGoodEditDataResponse,
        LentGoodRequest
        > {

    @Autowired
    private LentGoodMapper lentGoodMapper;

    public LentGoodService(
            UserProfileRepository userProfileRepository,
            ContactRepository contactRepository,
            ItemRepository itemRepository,
            CurrencyRepository currencyRepository,
            BorrowedGoodRepository borrowedGoodRepository,
            LentGoodRepository lentGoodRepository,
            AuthService authService,
            ContactMapper contactMapper,
            ItemMapper itemMapper,
            CurrencyMapper currencyMapper
    ) {
        super(
                userProfileRepository,
                contactRepository,
                itemRepository,
                currencyRepository,
                borrowedGoodRepository,
                lentGoodRepository,
                authService,
                contactMapper,
                itemMapper,
                currencyMapper
        );
    }

    public LentGoodsResponse getLentGoods(HttpServletRequest request) {
        UUID userId = getCurrentUserId(request);
        List<LentGood> list = safeRepo(
                () -> lentGoodRepository.findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(userId),
                "Failed to fetch lent goods"
        );
        return lentGoodMapper.mapToLentGoodsResponse(list);
    }

    public LentGoodResponse getLentGood(HttpServletRequest request, int id) {
        UUID userId = getCurrentUserId(request);
        LentGood entity = getEntityById(
                safeRepo(() -> lentGoodRepository.findById(id), "Failed to fetch lent good"),
                "Lent good not found"
        );
        checkOwnership(entity.getUserProfile().getSupabaseUserId(), userId,
                "Lent good does not belong to this user");
        return lentGoodMapper.mapToLentGoodResponse(entity);
    }

    public LentGoodAddDataResponse getLentGoodAddData(HttpServletRequest request) {
        UUID userId = getCurrentUserId(request);
        LentGoodAddDataResponse response = new LentGoodAddDataResponse();
        response.setContacts(contactMapper.mapToContactResponseList(getOrderedContacts(userId)));
        response.setItems(itemMapper.mapToItemResponseList(getOrderedItems(userId)));
        response.setCurrencies(currencyMapper.mapToCurrencyResponseList(getOrderedCurrencies(userId)));
        return response;
    }

    public LentGoodEditDataResponse getLentGoodEditData(HttpServletRequest request, int id) {
        UUID userId = getCurrentUserId(request);
        LentGood entity = getEntityById(
                safeRepo(() -> lentGoodRepository.findById(id), "Failed to fetch lent good"),
                "Lent good not found"
        );
        checkOwnership(entity.getUserProfile().getSupabaseUserId(), userId,
                "Lent good does not belong to this user");
        LentGoodEditDataResponse response = new LentGoodEditDataResponse();
        response.setContacts(contactMapper.mapToContactResponseList(getOrderedContacts(userId)));
        response.setItems(itemMapper.mapToItemResponseList(getOrderedItems(userId)));
        response.setCurrencies(currencyMapper.mapToCurrencyResponseList(getOrderedCurrencies(userId)));
        response.setLentGood(lentGoodMapper.mapToLentGoodResponse(entity));
        return response;
    }

    public LentGoodAddResponse addLentGood(HttpServletRequest request, LentGoodRequest dto) {
        UUID userId = getCurrentUserId(request);
        UserProfile profile = getUserProfile(userId);
        Contact contact = getContactOrThrow(dto.getContactId(), userId);
        Item item = getItemOrThrow(dto.getItemId(), userId);
        Currency currency = getCurrencyOrThrow(dto.getCurrencyId());
        validateItemOrCurrency(dto.getItemId(), dto.getCurrencyId());
        LentGood entity = lentGoodMapper.mapToLentGood(dto, profile, contact, item, currency);
        safeRepo(() -> lentGoodRepository.save(entity), "Failed to save lent good");
        return createResponse("Lent good added successfully", LentGoodAddResponse.class);
    }

    public LentGoodEditResponse editLentGood(HttpServletRequest request, LentGoodRequest dto, int id) {
        UUID userId = getCurrentUserId(request);
        LentGood entity = getEntityById(
                safeRepo(() -> lentGoodRepository.findById(id), "Failed to fetch lent good"),
                "Lent good not found"
        );
        checkOwnership(entity.getUserProfile().getSupabaseUserId(), userId,
                "Lent good does not belong to this user");
        Contact contact = getContactOrThrow(dto.getContactId(), userId);
        Item item = getItemOrThrow(dto.getItemId(), userId);
        Currency currency = getCurrencyOrThrow(dto.getCurrencyId());
        validateItemOrCurrency(dto.getItemId(), dto.getCurrencyId());
        lentGoodMapper.updateLentGood(entity, dto, contact, item, currency);
        safeRepo(() -> lentGoodRepository.save(entity), "Failed to edit lent good");
        return createResponse("Lent good edited successfully", LentGoodEditResponse.class);
    }

    public LentGoodDeleteResponse deleteLentGood(HttpServletRequest request, int id) {
        UUID userId = getCurrentUserId(request);
        LentGood entity = getEntityById(
                safeRepo(() -> lentGoodRepository.findById(id), "Failed to fetch lent good"),
                "Lent good not found"
        );
        checkOwnership(entity.getUserProfile().getSupabaseUserId(), userId,
                "Lent good does not belong to this user");
        entity.setDeleted(true);
        safeRepo(() -> lentGoodRepository.save(entity), "Failed to delete lent good");
        return createResponse("Lent good deleted successfully", LentGoodDeleteResponse.class);
    }
}
