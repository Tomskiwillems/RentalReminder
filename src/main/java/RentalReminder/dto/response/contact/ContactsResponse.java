package RentalReminder.dto.response.contact;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;
import java.util.Map;

public class ContactsResponse extends BaseResponseDto {

    private List<Map<String, Object>> contacts;

    public List<Map<String, Object>> getContacts() {
        return contacts;
    }
    public void setContacts(List<Map<String, Object>> contacts) {
        this.contacts = contacts;
    }
}
