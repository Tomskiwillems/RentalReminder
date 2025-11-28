package RentalReminder.dto.response;

import java.util.Map;

public class DashboardResponse {
    private String message;
    private Map<String, Object> gridViewItems;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getGridViewItems() {
        return gridViewItems;
    }

    public void setGridViewItems(Map<String, Object> gridViewItems) {
        this.gridViewItems = gridViewItems;
    }
}
