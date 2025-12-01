package RentalReminder.controller;

import RentalReminder.dto.request.CurrencyRequest;
import RentalReminder.dto.response.currency.*;
import RentalReminder.service.CurrencyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController extends BaseController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("")
    public ResponseEntity<CurrenciesResponse> getCurrencies(HttpServletRequest request) {
        return handle(
                CurrenciesResponse::new,
                response -> currencyService.getCurrencies(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> getCurrency(HttpServletRequest request, @PathVariable int id) {
        return handle(
                CurrencyResponse::new,
                response -> currencyService.getCurrency(request, id)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<CurrencyAddResponse> addCurrency(
            HttpServletRequest request,
            @RequestBody CurrencyRequest dto) {

        return handle(
                CurrencyAddResponse::new,
                response -> currencyService.addCurrency(request, dto)
        );
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<CurrencyEditResponse> editCurrency(
            HttpServletRequest request,
            @PathVariable int id,
            @RequestBody CurrencyRequest dto) {

        return handle(
                CurrencyEditResponse::new,
                response -> currencyService.editCurrency(request, dto, id)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CurrencyDeleteResponse> deleteCurrency(
            HttpServletRequest request,
            @PathVariable int id) {

        return handle(
                CurrencyDeleteResponse::new,
                response -> currencyService.deleteCurrency(request, id)
        );
    }
}
