package RentalReminder.mapper;

import RentalReminder.dto.request.ContactRequest;
import RentalReminder.dto.response.contact.ContactResponse;
import RentalReminder.dto.response.contact.ContactsResponse;
import RentalReminder.entity.Contact;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactMapper {

    public ContactsResponse mapToContactsResponse(List<Contact> contacts) {
        ContactsResponse contactsResponse = new ContactsResponse();
        contactsResponse.setContacts(contacts.stream()
                .map(g -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", g.getId());
                    map.put("name", g.getName());
                    map.put("description", g.getDescription());
                    return map;
                })
                .toList());
        return contactsResponse;
    }

    public ContactResponse mapToContactResponse(Contact contact) {
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setName(contact.getName());
        contactResponse.setDescription(contact.getDescription());
        return contactResponse;
    }

    public Contact mapToContact(ContactRequest contactRequest, UserProfile userProfile) {
        Contact contact = new Contact();
        contact.setName(contactRequest.getName());
        contact.setDescription(contactRequest.getDescription());
        contact.setUserProfile(userProfile);
        return contact;
    }
}
