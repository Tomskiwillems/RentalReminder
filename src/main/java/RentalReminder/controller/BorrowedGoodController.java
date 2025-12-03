package RentalReminder.controller;

import RentalReminder.dto.request.BorrowedGoodRequest;
import RentalReminder.dto.response.borrowedgood.*;
import RentalReminder.service.BorrowedGoodService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrowed-goods")
public class BorrowedGoodController extends BaseController {

    @Autowired
    private BorrowedGoodService borrowedGoodService;

    @GetMapping("")
    public ResponseEntity<BorrowedGoodsResponse> getBorrowedGoods(HttpServletRequest request) {
        return handle(
                BorrowedGoodsResponse::new,
                response -> borrowedGoodService.getBorrowedGoods(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowedGoodResponse> getBorrowedGood(HttpServletRequest request, @PathVariable int id) {
        return handle(
                BorrowedGoodResponse::new,
                response -> borrowedGoodService.getBorrowedGood(request, id)
        );
    }

    @GetMapping("/add/data")
    public ResponseEntity<BorrowedGoodAddDataResponse> getBorrowedGoodAddData(HttpServletRequest request) {
        return handle(
                BorrowedGoodAddDataResponse::new,
                response -> borrowedGoodService.getBorrowedGoodAddData(request)
        );
    }

    @GetMapping("/edit/data/{id}")
    public ResponseEntity<BorrowedGoodEditDataResponse> getBorrowedGoodEditData(
            HttpServletRequest request,
            @PathVariable int id) {
        return handle(
                BorrowedGoodEditDataResponse::new,
                response -> borrowedGoodService.getBorrowedGoodEditData(request, id)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<BorrowedGoodAddResponse> addBorrowedGood(
            HttpServletRequest request,
            @RequestBody BorrowedGoodRequest dto) {
        return handle(
                BorrowedGoodAddResponse::new,
                response -> borrowedGoodService.addBorrowedGood(request, dto)
        );
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<BorrowedGoodEditResponse> editBorrowedGood(
            HttpServletRequest request,
            @PathVariable int id,
            @RequestBody BorrowedGoodRequest dto) {
        return handle(
                BorrowedGoodEditResponse::new,
                response -> borrowedGoodService.editBorrowedGood(request, dto, id)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BorrowedGoodDeleteResponse> deleteBorrowedGood(
            HttpServletRequest request,
            @PathVariable int id) {
        return handle(
                BorrowedGoodDeleteResponse::new,
                response -> borrowedGoodService.deleteBorrowedGood(request, id)
        );
    }
}
