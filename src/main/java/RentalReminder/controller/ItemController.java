package RentalReminder.controller;

import RentalReminder.dto.request.ItemRequest;
import RentalReminder.dto.response.item.*;
import RentalReminder.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @GetMapping("")
    public ResponseEntity<ItemsResponse> getItems(HttpServletRequest request) {
        return handle(
                ItemsResponse::new,
                response -> itemService.getItems(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(HttpServletRequest request, @PathVariable int id) {
        return handle(
                ItemResponse::new,
                response -> itemService.getItem(request, id)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ItemAddResponse> addItem(
            HttpServletRequest request,
            @Valid @RequestBody ItemRequest dto) {

        return handle(
                ItemAddResponse::new,
                response -> itemService.addItem(request, dto)
        );
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<ItemEditResponse> editItem(
            HttpServletRequest request,
            @PathVariable int id,
            @Valid @RequestBody ItemRequest dto) {

        return handle(
                ItemEditResponse::new,
                response -> itemService.editItem(request, dto, id)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ItemDeleteResponse> deleteItem(
            HttpServletRequest request,
            @PathVariable int id) {

        return handle(
                ItemDeleteResponse::new,
                response -> itemService.deleteItem(request, id)
        );
    }
}
