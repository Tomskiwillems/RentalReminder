package RentalReminder.dto.response.lentgood;

import RentalReminder.dto.response.BaseResponseDto;

import java.util.List;
import java.util.Map;

public class LentGoodsResponse extends BaseResponseDto {

    private List<LentGoodResponse> lentGoods;

    public List<LentGoodResponse> getLentGoods() {
        return lentGoods;
    }

    public void setLentGoods(List<LentGoodResponse> lentGoods) {
        this.lentGoods = lentGoods;
    }
}
