package RentalReminder.service;

import RentalReminder.dto.response.BaseResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseService {

    // Get current user ID from request cookie
    protected UUID getCurrentUserId(HttpServletRequest request, AuthService authService) {
        String accessToken = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(c -> c.getName().equals("access_token"))
                        .findFirst()
                        .map(Cookie::getValue))
                .orElse(null);

        if (accessToken == null) {
            throw new RuntimeException("Access token missing");
        }

        return authService.verifyTokenAndGetUserId(accessToken);
    }

    // Ownership check
    protected void checkOwnership(UUID entityOwnerId, UUID currentUserId, String errorMsg) {
        if (!entityOwnerId.equals(currentUserId)) {
            throw new RuntimeException(errorMsg);
        }
    }

    // Fetch entity from Optional or throw
    protected <E> E getEntityById(Optional<E> optionalEntity, String notFoundMsg) {
        return optionalEntity.orElseThrow(() -> new RuntimeException(notFoundMsg));
    }

    // Create a response DTO instance with a message
    protected <R extends BaseResponseDto> R createResponse(String message, Class<R> clazz) {
        try {
            R response = clazz.getDeclaredConstructor().newInstance();
            response.setMessage(message);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create response instance", e);
        }
    }
}

