package RentalReminder.mapper;

import RentalReminder.dto.request.BorrowedGoodRequest;
import RentalReminder.dto.response.borrowedgood.BorrowedGoodResponse;
import RentalReminder.dto.response.borrowedgood.BorrowedGoodsResponse;
import RentalReminder.entity.BorrowedGood;
import RentalReminder.entity.Contact;
import RentalReminder.entity.Currency;
import RentalReminder.entity.Item;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BorrowedGoodMapper {

    // Map a list of BorrowedGoods to grid view response
    public BorrowedGoodsResponse mapToBorrowedGoodsResponse(List<BorrowedGood> borrowedGoods) {
        BorrowedGoodsResponse response = new BorrowedGoodsResponse();
        response.setBorrowedGoods(borrowedGoods.stream()
                .map(bg -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", bg.getId());
                    map.put("contact", bg.getContact().getName());
                    map.put("good", bg.getGoodName());
                    map.put("amount", bg.getAmount());
                    map.put("startDate", bg.getStartDate());
                    map.put("endDate", bg.getEndDate());
                    return map;
                })
                .toList());
        return response;
    }

    // Map a single BorrowedGood to response DTO for edit/view
    public BorrowedGoodResponse mapToBorrowedGoodResponse(BorrowedGood borrowedGood) {
        BorrowedGoodResponse response = new BorrowedGoodResponse();
        response.setId(borrowedGood.getId());
        response.setContactId(borrowedGood.getContact().getId());
        response.setItemId(borrowedGood.getItem() != null ? borrowedGood.getItem().getId() : null);
        response.setCurrencyId(borrowedGood.getCurrency() != null ? borrowedGood.getCurrency().getId() : null);
        response.setAmount(borrowedGood.getAmount());
        response.setEndDate(borrowedGood.getEndDate());
        return response;
    }

    // Map a request DTO + related entities to BorrowedGood entity
    public BorrowedGood mapToBorrowedGood(BorrowedGoodRequest request,
                                          UserProfile userProfile,
                                          Contact contact,
                                          Item item,
                                          Currency currency) {
        BorrowedGood borrowedGood = new BorrowedGood();
        borrowedGood.setUserProfile(userProfile);
        borrowedGood.setContact(contact);
        borrowedGood.setItem(item);       // may be null
        borrowedGood.setCurrency(currency); // may be null
        borrowedGood.setAmount(request.getAmount());
        borrowedGood.setStartDate(request.getStartDate());
        borrowedGood.setEndDate(request.getEndDate());
        return borrowedGood;
    }
}
