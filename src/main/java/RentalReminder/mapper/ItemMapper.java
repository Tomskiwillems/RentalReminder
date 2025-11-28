package RentalReminder.mapper;

import RentalReminder.dto.request.ItemRequest;
import RentalReminder.dto.response.item.ItemResponse;
import RentalReminder.dto.response.item.ItemsResponse;
import RentalReminder.entity.Item;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemMapper {

    // Map a list of Items to ItemsResponse (list of maps)
    public ItemsResponse mapToItemsResponse(List<Item> items) {
        ItemsResponse itemsResponse = new ItemsResponse();
        itemsResponse.setItems(items.stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", item.getId());
                    map.put("name", item.getName());
                    map.put("description", item.getDescription());
                    return map;
                })
                .toList());
        return itemsResponse;
    }

    // Map a single Item entity to ItemResponse DTO
    public ItemResponse mapToItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(item.getId());
        itemResponse.setName(item.getName());
        itemResponse.setDescription(item.getDescription());
        return itemResponse;
    }

    // Map an ItemRequest + UserProfile to an Item entity (for saving/updating)
    public Item mapToItem(ItemRequest itemRequest, UserProfile userProfile) {
        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setUserProfile(userProfile);
        return item;
    }
}

