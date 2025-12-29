package RentalReminder.mapper;

import RentalReminder.dto.request.ItemRequest;
import RentalReminder.dto.response.item.ItemResponse;
import RentalReminder.dto.response.item.ItemsResponse;
import RentalReminder.entity.Item;
import RentalReminder.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemMapper {

    public ItemsResponse mapToItemsResponse(List<Item> items) {
        ItemsResponse itemsResponse = new ItemsResponse();
        List<ItemResponse> itemResponses = items.stream()
                .map(this::mapToItemResponse)
                .collect(Collectors.toList());
        itemsResponse.setItems(itemResponses);
        return itemsResponse;
    }

    public List<ItemResponse> mapToItemResponseList(List<Item> items) {
        return items.stream()
                .map(this::mapToItemResponse)
                .collect(Collectors.toList());
    }

    public ItemResponse mapToItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(item.getId());
        itemResponse.setName(item.getName());
        itemResponse.setDescription(item.getDescription());
        return itemResponse;
    }

    public Item mapToItem(ItemRequest itemRequest, UserProfile userProfile) {
        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setUserProfile(userProfile);
        return item;
    }
}

