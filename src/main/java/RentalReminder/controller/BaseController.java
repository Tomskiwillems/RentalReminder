package RentalReminder.controller;

import RentalReminder.dto.response.BaseResponseDto;
import RentalReminder.exception.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseController {

    protected <T extends BaseResponseDto> ResponseEntity<T> handle(Supplier<T> dtoSupplier, Function<T, T> action) {
        T response = dtoSupplier.get();
        try {
            T result = action.apply(response);
            HttpHeaders httpHeaders = result.getHttpHeaders() != null ? result.getHttpHeaders() : new HttpHeaders();
            return ResponseEntity.ok().headers(httpHeaders).body(result);
        } catch (ApiException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(response);
        }
    }
}
