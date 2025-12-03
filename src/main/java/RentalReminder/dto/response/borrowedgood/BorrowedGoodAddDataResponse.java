package RentalReminder.dto.response.borrowedgood;

import RentalReminder.dto.response.BaseResponseDto;
import RentalReminder.dto.response.contact.ContactResponse;
import RentalReminder.dto.response.currency.CurrencyResponse;
import RentalReminder.dto.response.item.ItemResponse;

import java.util.List;

public class BorrowedGoodAddDataResponse extends BaseResponseDto {

    private List<ContactResponse> contacts;
    private List<ItemResponse> items;
    private List<CurrencyResponse> currencies;

    public List<ContactResponse> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactResponse> contacts) {
        this.contacts = contacts;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }

    public List<CurrencyResponse> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<CurrencyResponse> currencies) {
        this.currencies = currencies;
    }
}
