package RentalReminder.controller;

import RentalReminder.dto.request.LentGoodRequest;
import RentalReminder.dto.response.lentgood.*;
import RentalReminder.service.LentGoodService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lent-goods")
public class LentGoodController extends BaseController {

    @Autowired
    private LentGoodService lentGoodService;

    @GetMapping("")
    public ResponseEntity<LentGoodsResponse> getLentGoods(HttpServletRequest request) {
        return handle(
                LentGoodsResponse::new,
                response -> lentGoodService.getLentGoods(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LentGoodResponse> getLentGood(HttpServletRequest request, @PathVariable int id) {
        return handle(
                LentGoodResponse::new,
                response -> lentGoodService.getLentGood(request, id)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<LentGoodAddResponse> addLentGood(
            HttpServletRequest request,
            @RequestBody LentGoodRequest dto) {

        return handle(
                LentGoodAddResponse::new,
                response -> lentGoodService.addLentGood(request, dto)
        );
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<LentGoodEditResponse> editLentGood(
            HttpServletRequest request,
            @PathVariable int id,
            @RequestBody LentGoodRequest dto) {

        return handle(
                LentGoodEditResponse::new,
                response -> lentGoodService.editLentGood(request, dto, id)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<LentGoodDeleteResponse> deleteLentGood(
            HttpServletRequest request,
            @PathVariable int id) {

        return handle(
                LentGoodDeleteResponse::new,
                response -> lentGoodService.deleteLentGood(request, id)
        );
    }
}
