package RentalReminder.mapper;

import RentalReminder.dto.request.BorrowedGoodRequest;
import RentalReminder.dto.response.borrowedgood.*;
import RentalReminder.entity.BorrowedGood;
import RentalReminder.entity.Contact;
import RentalReminder.entity.Currency;
import RentalReminder.entity.Item;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowedGoodMapper {

    private final ContactMapper contactMapper;
    private final ItemMapper itemMapper;
    private final CurrencyMapper currencyMapper;

    public BorrowedGoodMapper(ContactMapper contactMapper, ItemMapper itemMapper, CurrencyMapper currencyMapper) {
        this.contactMapper = contactMapper;
        this.itemMapper = itemMapper;
        this.currencyMapper = currencyMapper;
    }

    public BorrowedGoodResponse mapToBorrowedGoodResponse(BorrowedGood entity) {
        BorrowedGoodResponse dto = new BorrowedGoodResponse();
        dto.setId(entity.getId());
        dto.setContact(entity.getContact() != null ? contactMapper.mapToContactResponse(entity.getContact()) : null);
        dto.setItem(entity.getItem() != null ? itemMapper.mapToItemResponse(entity.getItem()) : null);
        dto.setCurrency(entity.getCurrency() != null ? currencyMapper.mapToCurrencyResponse(entity.getCurrency()) : null);
        dto.setAmount(entity.getAmount());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

    public BorrowedGoodsResponse mapToBorrowedGoodsResponse(List<BorrowedGood> list) {
        BorrowedGoodsResponse response = new BorrowedGoodsResponse();
        response.setBorrowedGoods(
                list.stream()
                        .map(this::mapToBorrowedGoodResponse)
                        .collect(Collectors.toList())
        );
        return response;
    }

    public BorrowedGood mapToBorrowedGood(
            BorrowedGoodRequest request,
            UserProfile userProfile,
            Contact contact,
            Item item,
            Currency currency) {
        BorrowedGood bg = new BorrowedGood();
        bg.setUserProfile(userProfile);
        bg.setContact(contact);
        bg.setItem(item);
        bg.setCurrency(currency);
        bg.setAmount(request.getAmount());
        bg.setEndDate(request.getEndDate());
        return bg;
    }

    public void updateBorrowedGood(
            BorrowedGood entity,
            BorrowedGoodRequest request,
            Contact contact,
            Item item,
            Currency currency) {
        entity.setContact(contact);
        entity.setItem(item);
        entity.setCurrency(currency);
        entity.setAmount(request.getAmount());
        entity.setEndDate(request.getEndDate());
    }
}
