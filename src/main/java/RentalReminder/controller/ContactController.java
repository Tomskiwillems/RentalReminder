package RentalReminder.controller;

import RentalReminder.dto.request.ContactRequest;
import RentalReminder.dto.response.contact.*;
import RentalReminder.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
public class ContactController extends BaseController {

    @Autowired
    private ContactService contactService;

    @GetMapping("")
    public ResponseEntity<ContactsResponse> getContacts(HttpServletRequest request) {
        return handle(() -> contactService.getContacts(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContact(HttpServletRequest request, @PathVariable int id) {
        return handle(() -> contactService.getContact(request, id));
    }

    @PostMapping("/add")
    public ResponseEntity<ContactAddResponse> addContact(HttpServletRequest request, @RequestBody ContactRequest dto) {
        return handle(() -> contactService.addContact(request, dto));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<ContactEditResponse> editContact(
            HttpServletRequest request,
            @PathVariable int id,
            @RequestBody ContactRequest dto) {
        return handle(() -> contactService.editContact(request, dto, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ContactDeleteResponse> deleteContact(HttpServletRequest request, @PathVariable int id) {
        return handle(() -> contactService.deleteContact(request, id));
    }
}

