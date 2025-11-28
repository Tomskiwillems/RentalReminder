package RentalReminder.dto.response.item;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;
import java.util.Map;

public class ItemsResponse extends BaseResponseDto {

    private List<Map<String, Object>> items;

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }
}
