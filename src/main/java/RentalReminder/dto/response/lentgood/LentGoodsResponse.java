package RentalReminder.dto.response.lentgood;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;
import java.util.Map;

public class LentGoodsResponse extends BaseResponseDto {

    private List<Map<String, Object>> lentGoods;

    public List<Map<String, Object>> getLentGoods() {
        return lentGoods;
    }

    public void setLentGoods(List<Map<String, Object>> lentGoods) {
        this.lentGoods = lentGoods;
    }
}
