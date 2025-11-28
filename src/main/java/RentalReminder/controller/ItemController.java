package RentalReminder.controller;

import RentalReminder.dto.request.ItemRequest;
import RentalReminder.dto.response.item.*;
import RentalReminder.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
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
        return handle(() -> itemService.getItems(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(HttpServletRequest request, @PathVariable int id) {
        return handle(() -> itemService.getItem(request, id));
    }

    @PostMapping("/add")
    public ResponseEntity<ItemAddResponse> addItem(HttpServletRequest request, @RequestBody ItemRequest dto) {
        return handle(() -> itemService.addItem(request, dto));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<ItemEditResponse> editItem(
            HttpServletRequest request,
            @PathVariable int id,
            @RequestBody ItemRequest dto) {
        return handle(() -> itemService.editItem(request, dto, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ItemDeleteResponse> deleteItem(HttpServletRequest request, @PathVariable int id) {
        return handle(() -> itemService.deleteItem(request, id));
    }
}
