package RentalReminder.dto.response.item;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;

public class ItemsResponse extends BaseResponseDto {

    private List<ItemResponse> items;

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }
}
