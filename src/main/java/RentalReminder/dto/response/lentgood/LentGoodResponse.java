package RentalReminder.dto.response.lentgood;

import RentalReminder.dto.response.BaseResponseDto;
import RentalReminder.dto.response.contact.ContactResponse;
import RentalReminder.dto.response.currency.CurrencyResponse;
import RentalReminder.dto.response.item.ItemResponse;

import java.time.LocalDateTime;

public class LentGoodResponse extends BaseResponseDto {

    private int id;
    private ContactResponse contact;
    private ItemResponse item;
    private CurrencyResponse currency;
    private Integer amount;
    private LocalDateTime endDate;
    private LocalDateTime startDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContactResponse getContact() {
        return contact;
    }

    public void setContact(ContactResponse contact) {
        this.contact = contact;
    }

    public ItemResponse getItem() {
        return item;
    }

    public void setItem(ItemResponse item) {
        this.item = item;
    }

    public CurrencyResponse getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyResponse currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}