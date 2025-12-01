package RentalReminder.dto.response;

import java.util.Map;

public class DashboardResponse extends BaseResponseDto {
    private Map<String, Object> gridViewItems;

    public Map<String, Object> getGridViewItems() {
        return gridViewItems;
    }

    public void setGridViewItems(Map<String, Object> gridViewItems) {
        this.gridViewItems = gridViewItems;
    }
}
