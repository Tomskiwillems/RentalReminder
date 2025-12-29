package RentalReminder.service;

import RentalReminder.dto.request.ContactRequest;
import RentalReminder.dto.response.contact.*;
import RentalReminder.entity.Contact;
import RentalReminder.entity.UserProfile;
import RentalReminder.mapper.ContactMapper;
import RentalReminder.repository.ContactRepository;
import RentalReminder.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService extends BaseService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ContactMapper contactMapper;

    // Get all contacts for the current user
    public ContactsResponse getContacts(HttpServletRequest request) {
        UUID currentUserId = getCurrentUserId(request, authService);
        List<Contact> contacts = safeRepo(
                () -> contactRepository.findByUserProfileSupabaseUserId(currentUserId),
                "Failed to fetch contacts"
        );
        return contactMapper.mapToContactsResponse(contacts);
    }

    // Get a single contact
    public ContactResponse getContact(HttpServletRequest request, int contactId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Contact contact = getEntityById(
                safeRepo(() -> contactRepository.findById(contactId), "Failed to fetch contact"),
                "Contact not found"
        );
        checkOwnership(contact.getUserProfile().getSupabaseUserId(), currentUserId,
                "Contact does not belong to this user");
        return contactMapper.mapToContactResponse(contact);
    }

    // Add a contact
    public ContactAddResponse addContact(HttpServletRequest request, ContactRequest contactRequest) {
        UUID currentUserId = getCurrentUserId(request, authService);
        UserProfile userProfile = getEntityById(
                safeRepo(() -> userProfileRepository.findBySupabaseUserId(currentUserId), "Failed to fetch user profile"),
                "UserProfile not found"
        );
        Contact contact = contactMapper.mapToContact(contactRequest, userProfile);
        safeRepo(() -> contactRepository.save(contact), "Failed to add contact");
        return createResponse("Contact added successfully", ContactAddResponse.class);
    }

    // Edit a contact
    public ContactEditResponse editContact(HttpServletRequest request, ContactRequest contactRequest, int contactId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Contact contact = getEntityById(
                safeRepo(() -> contactRepository.findById(contactId), "Failed to fetch contact"),
                "Contact not found"
        );
        checkOwnership(contact.getUserProfile().getSupabaseUserId(), currentUserId,
                "Contact does not belong to this user");
        contact.setName(contactRequest.getName());
        contact.setDescription(contactRequest.getDescription());
        safeRepo(() -> contactRepository.save(contact), "Failed to edit contact");
        return createResponse("Contact edited successfully", ContactEditResponse.class);
    }

    // Delete a contact
    public ContactDeleteResponse deleteContact(HttpServletRequest request, int contactId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Contact contact = getEntityById(
                safeRepo(() -> contactRepository.findById(contactId), "Failed to fetch contact"),
                "Contact not found"
        );
        checkOwnership(contact.getUserProfile().getSupabaseUserId(), currentUserId,
                "Contact does not belong to this user");
        safeRepoVoid(() -> contactRepository.delete(contact), "Failed to delete contact");
        return createResponse("Contact deleted successfully", ContactDeleteResponse.class);
    }
}
