package RentalReminder.service;

import RentalReminder.dto.request.ItemRequest;
import RentalReminder.dto.response.item.*;
import RentalReminder.entity.Item;
import RentalReminder.entity.UserProfile;
import RentalReminder.mapper.ItemMapper;
import RentalReminder.repository.ItemRepository;
import RentalReminder.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService extends BaseService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ItemMapper itemMapper;

    // Get all items for the current user
    public ItemsResponse getItems(HttpServletRequest request) {
        UUID currentUserId = getCurrentUserId(request, authService);
        List<Item> items = safeRepo(
                () -> itemRepository.findByUserProfileSupabaseUserId(currentUserId),
                "Failed to fetch items"
        );
        return itemMapper.mapToItemsResponse(items);
    }

    // Get a single item
    public ItemResponse getItem(HttpServletRequest request, int itemId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Item item = getEntityById(
                safeRepo(() -> itemRepository.findById(itemId), "Failed to fetch item"),
                "Item not found"
        );
        checkOwnership(item.getUserProfile().getSupabaseUserId(), currentUserId,
                "Item does not belong to this user");
        return itemMapper.mapToItemResponse(item);
    }

    // Add an item
    public ItemAddResponse addItem(HttpServletRequest request, ItemRequest itemRequest) {
        UUID currentUserId = getCurrentUserId(request, authService);
        UserProfile userProfile = getEntityById(
                safeRepo(() -> userProfileRepository.findBySupabaseUserId(currentUserId), "Failed to fetch user profile"),
                "UserProfile not found"
        );
        Item item = itemMapper.mapToItem(itemRequest, userProfile);
        safeRepo(() -> itemRepository.save(item), "Failed to add item");
        return createResponse("Item added successfully", ItemAddResponse.class);
    }

    // Edit an item
    public ItemEditResponse editItem(HttpServletRequest request, ItemRequest itemRequest, int itemId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Item item = getEntityById(
                safeRepo(() -> itemRepository.findById(itemId), "Failed to fetch item"),
                "Item not found"
        );
        checkOwnership(item.getUserProfile().getSupabaseUserId(), currentUserId,
                "Item does not belong to this user");
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        safeRepo(() -> itemRepository.save(item), "Failed to edit item");
        return createResponse("Item edited successfully", ItemEditResponse.class);
    }

    // Delete an item
    public ItemDeleteResponse deleteItem(HttpServletRequest request, int itemId) {
        UUID currentUserId = getCurrentUserId(request, authService);
        Item item = getEntityById(
                safeRepo(() -> itemRepository.findById(itemId), "Failed to fetch item"),
                "Item not found"
        );
        checkOwnership(item.getUserProfile().getSupabaseUserId(), currentUserId,
                "Item does not belong to this user");
        safeRepoVoid(() -> itemRepository.delete(item), "Failed to delete item");
        return createResponse("Item deleted successfully", ItemDeleteResponse.class);
    }
}
