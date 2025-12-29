package RentalReminder.controller;

import RentalReminder.dto.request.ContactRequest;
import RentalReminder.dto.response.contact.*;
import RentalReminder.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
        return handle(
                ContactsResponse::new,
                response -> contactService.getContacts(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContact(HttpServletRequest request, @PathVariable int id) {
        return handle(
                ContactResponse::new,
                response -> contactService.getContact(request, id)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ContactAddResponse> addContact(
            HttpServletRequest request,
            @Valid @RequestBody ContactRequest dto) {
        return handle(
                ContactAddResponse::new,
                response -> contactService.addContact(request, dto)
        );
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity<ContactEditResponse> editContact(
            HttpServletRequest request,
            @PathVariable int id,
            @Valid @RequestBody ContactRequest dto) {
        return handle(
                ContactEditResponse::new,
                response -> contactService.editContact(request, dto, id)
        );
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ContactDeleteResponse> deleteContact(
            HttpServletRequest request,
            @PathVariable int id) {
        return handle(
                ContactDeleteResponse::new,
                response -> contactService.deleteContact(request, id)
        );
    }

}

