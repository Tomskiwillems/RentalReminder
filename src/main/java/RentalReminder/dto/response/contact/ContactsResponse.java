package RentalReminder.dto.response.contact;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;

public class ContactsResponse extends BaseResponseDto {

    private List<ContactResponse> contacts;

    public List<ContactResponse> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactResponse> contacts) {
        this.contacts = contacts;
    }
}
