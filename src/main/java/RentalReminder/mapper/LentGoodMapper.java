package RentalReminder.mapper;

import RentalReminder.dto.request.LentGoodRequest;
import RentalReminder.dto.response.lentgood.*;
import RentalReminder.entity.LentGood;
import RentalReminder.entity.Contact;
import RentalReminder.entity.Currency;
import RentalReminder.entity.Item;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LentGoodMapper {

    private final ContactMapper contactMapper;
    private final ItemMapper itemMapper;
    private final CurrencyMapper currencyMapper;

    public LentGoodMapper(ContactMapper contactMapper, ItemMapper itemMapper, CurrencyMapper currencyMapper) {
        this.contactMapper = contactMapper;
        this.itemMapper = itemMapper;
        this.currencyMapper = currencyMapper;
    }

    public LentGoodResponse mapToLentGoodResponse(LentGood entity) {
        LentGoodResponse dto = new LentGoodResponse();
        dto.setId(entity.getId());
        dto.setContact(entity.getContact() != null ? contactMapper.mapToContactResponse(entity.getContact()) : null);
        dto.setItem(entity.getItem() != null ? itemMapper.mapToItemResponse(entity.getItem()) : null);
        dto.setCurrency(entity.getCurrency() != null ? currencyMapper.mapToCurrencyResponse(entity.getCurrency()) : null);
        dto.setAmount(entity.getAmount());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

    public LentGoodsResponse mapToLentGoodsResponse(List<LentGood> list) {
        LentGoodsResponse response = new LentGoodsResponse();
        response.setLentGoods(
                list.stream()
                        .map(this::mapToLentGoodResponse)
                        .collect(Collectors.toList())
        );
        return response;
    }

    public LentGood mapToLentGood(
            LentGoodRequest request,
            UserProfile userProfile,
            Contact contact,
            Item item,
            Currency currency) {
        LentGood lg = new LentGood();
        lg.setUserProfile(userProfile);
        lg.setContact(contact);
        lg.setItem(item);
        lg.setCurrency(currency);
        lg.setAmount(request.getAmount());
        lg.setEndDate(request.getEndDate());
        return lg;
    }

    public void updateLentGood(
            LentGood entity,
            LentGoodRequest request,
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
