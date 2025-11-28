package RentalReminder.dto.request;

import RentalReminder.entity.Contact;
import RentalReminder.entity.Currency;
import RentalReminder.entity.Item;
import java.time.LocalDateTime;

public class LentGoodRequest {

    private int id;
    private Contact contact;
    private Item item;
    private Currency currency;
    private int amount;
    private LocalDateTime end_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }
}
