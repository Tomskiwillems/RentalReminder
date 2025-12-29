package RentalReminder.mapper;

import RentalReminder.dto.request.ContactRequest;
import RentalReminder.dto.response.contact.ContactResponse;
import RentalReminder.dto.response.contact.ContactsResponse;
import RentalReminder.entity.Contact;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactMapper {

    public ContactsResponse mapToContactsResponse(List<Contact> contacts) {
        ContactsResponse contactsResponse = new ContactsResponse();
        List<ContactResponse> contactResponses = contacts.stream()
                .map(this::mapToContactResponse)
                .collect(Collectors.toList());
        contactsResponse.setContacts(contactResponses);
        return contactsResponse;
    }

    public List<ContactResponse> mapToContactResponseList(List<Contact> contacts) {
        return contacts.stream()
                .map(this::mapToContactResponse)
                .collect(Collectors.toList());
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
