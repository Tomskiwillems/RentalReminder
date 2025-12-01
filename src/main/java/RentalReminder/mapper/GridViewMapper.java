package RentalReminder.mapper;

import RentalReminder.entity.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GridViewMapper {

    public Map<String, Object> mapLentAndBorrowedGoodsToMap(List<LentGood> lentGoods, List<BorrowedGood> borrowedGoods) {
        List<Map<String,Object>> lentGoodsDto = mapLentGoodsToList(lentGoods);
        List<Map<String,Object>> borrowedGoodsDto = mapBorrowedGoodsToList(borrowedGoods);
        Map<String, Object> response = new HashMap<>();
        response.put("borrowedGoods", borrowedGoodsDto);
        response.put("lentGoods", lentGoodsDto);
        return response;
    }

    public List<Map<String, Object>> mapLentGoodsToList(List<LentGood> lentGoods) {
        return lentGoods.stream()
                .map(g -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("contact", g.getContact().getName());
                    map.put("good", g.getGoodName());
                    map.put("amount", g.getAmount());
                    map.put("startDate", g.getStartDate());
                    map.put("endDate", g.getEndDate());
                    return map;
                })
                .toList();
    }

    public List<Map<String, Object>> mapBorrowedGoodsToList(List<BorrowedGood> borrowedGoods) {
        return borrowedGoods.stream()
                .map(g -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("contact", g.getContact().getName());
                    map.put("good", g.getGoodName());
                    map.put("amount", g.getAmount());
                    map.put("startDate", g.getStartDate());
                    map.put("endDate", g.getEndDate());
                    return map;
                })
                .toList();
    }
}
