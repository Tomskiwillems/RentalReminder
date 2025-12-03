package RentalReminder.service;

import RentalReminder.dto.request.BorrowedGoodRequest;
import RentalReminder.dto.response.borrowedgood.*;
import RentalReminder.entity.*;
import RentalReminder.mapper.BorrowedGoodMapper;
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
public class BorrowedGoodService extends BaseGoodService<
        BorrowedGood,
        BorrowedGoodAddResponse,
        BorrowedGoodEditResponse,
        BorrowedGoodAddDataResponse,
        BorrowedGoodEditDataResponse,
        BorrowedGoodRequest
        > {

    @Autowired
    private BorrowedGoodMapper borrowedGoodMapper;

    public BorrowedGoodService(
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

    public BorrowedGoodsResponse getBorrowedGoods(HttpServletRequest request) {
        UUID userId = getCurrentUserId(request);
        List<BorrowedGood> list = safeRepo(
                () -> borrowedGoodRepository.findByUserProfileSupabaseUserIdAndDeletedFalseOrderByEndDateAscStartDateAsc(userId),
                "Failed to fetch borrowed goods"
        );
        return borrowedGoodMapper.mapToBorrowedGoodsResponse(list);
    }

    public BorrowedGoodResponse getBorrowedGood(HttpServletRequest request, int id) {
        UUID userId = getCurrentUserId(request);
        BorrowedGood entity = getEntityById(
                safeRepo(() -> borrowedGoodRepository.findById(id), "Failed to fetch borrowed good"),
                "Borrowed good not found"
        );
        checkOwnership(entity.getUserProfile().getSupabaseUserId(), userId,
                "Borrowed good does not belong to this user");
        return borrowedGoodMapper.mapToBorrowedGoodResponse(entity);
    }

    public BorrowedGoodAddDataResponse getBorrowedGoodAddData(HttpServletRequest request) {
        UUID userId = getCurrentUserId(request);
        var response = new BorrowedGoodAddDataResponse();
        response.setContacts(
                contactMapper.mapToContactResponseList(getOrderedContacts(userId))
        );
        response.setItems(
                itemMapper.mapToItemResponseList(getOrderedItems(userId))
        );
        response.setCurrencies(
                currencyMapper.mapToCurrencyResponseList(getOrderedCurrencies(userId))
        );
        return response;
    }

    public BorrowedGoodEditDataResponse getBorrowedGoodEditData(HttpServletRequest request, int id) {
        UUID userId = getCurrentUserId(request);
        BorrowedGood entity = getEntityById(
                safeRepo(() -> borrowedGoodRepository.findById(id), "Failed to fetch borrowed good"),
                "Borrowed good not found"
        );
        checkOwnership(entity.getUserProfile().getSupabaseUserId(), userId,
                "Borrowed good does not belong to this user");
        BorrowedGoodEditDataResponse response = new BorrowedGoodEditDataResponse();
        response.setContacts(contactMapper.mapToContactResponseList(getOrderedContacts(userId)));
        response.setItems(itemMapper.mapToItemResponseList(getOrderedItems(userId)));
        response.setCurrencies(currencyMapper.mapToCurrencyResponseList(getOrderedCurrencies(userId)));
        response.setBorrowedGood(borrowedGoodMapper.mapToBorrowedGoodResponse(entity));
        return response;
    }

    public BorrowedGoodAddResponse addBorrowedGood(HttpServletRequest request, BorrowedGoodRequest dto) {
        UUID userId = getCurrentUserId(request);
        UserProfile profile = getUserProfile(userId);
        Contact contact = getContactOrThrow(dto.getContactId(), userId);
        Item item = getItemOrThrow(dto.getItemId(), userId);
        Currency currency = getCurrencyOrThrow(dto.getCurrencyId());
        validateItemOrCurrency(dto.getItemId(), dto.getCurrencyId());
        BorrowedGood entity = borrowedGoodMapper.mapToBorrowedGood(dto, profile, contact, item, currency);
        safeRepo(() -> borrowedGoodRepository.save(entity), "Failed to save borrowed good");
        return createResponse("Borrowed good added successfully", BorrowedGoodAddResponse.class);
    }

    public BorrowedGoodEditResponse editBorrowedGood(HttpServletRequest request, BorrowedGoodRequest dto, int id) {
        UUID userId = getCurrentUserId(request);
        BorrowedGood entity = getEntityById(
                safeRepo(() -> borrowedGoodRepository.findById(id), "Failed to fetch borrowed good"),
                "Borrowed good not found"
        );
        checkOwnership(entity.getUserProfile().getSupabaseUserId(), userId,
                "Borrowed good does not belong to this user");
        Contact contact = getContactOrThrow(dto.getContactId(), userId);
        Item item = getItemOrThrow(dto.getItemId(), userId);
        Currency currency = getCurrencyOrThrow(dto.getCurrencyId());
        validateItemOrCurrency(dto.getItemId(), dto.getCurrencyId());
        borrowedGoodMapper.updateBorrowedGood(entity, dto, contact, item, currency);
        safeRepo(() -> borrowedGoodRepository.save(entity), "Failed to edit borrowed good");
        return createResponse("Borrowed good edited successfully", BorrowedGoodEditResponse.class);
    }

    public BorrowedGoodDeleteResponse deleteBorrowedGood(HttpServletRequest request, int id) {
        UUID userId = getCurrentUserId(request);
        BorrowedGood entity = getEntityById(
                safeRepo(() -> borrowedGoodRepository.findById(id), "Failed to fetch borrowed good"),
                "Borrowed good not found"
        );
        checkOwnership(entity.getUserProfile().getSupabaseUserId(), userId,
                "Borrowed good does not belong to this user");

        entity.setDeleted(true);
        safeRepo(() -> borrowedGoodRepository.save(entity), "Failed to delete borrowed good");
        return createResponse("Borrowed good deleted successfully", BorrowedGoodDeleteResponse.class);
    }
}
