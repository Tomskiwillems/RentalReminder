package RentalReminder.controller;

import RentalReminder.dto.response.BaseResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public abstract class BaseController {

    protected <T extends BaseResponseDto> ResponseEntity<T> handle(Supplier<T> action) {
        try {
            T response = action.get();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            try {
                T errorResponse = action.get();
                errorResponse.setMessage(e.getMessage());
                return ResponseEntity.badRequest().body(errorResponse);
            } catch (Exception ex) {
                throw new RuntimeException("Error creating response object", ex);
            }
        }
    }
}

