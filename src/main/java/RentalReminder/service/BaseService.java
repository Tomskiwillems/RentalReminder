package RentalReminder.service;

import RentalReminder.dto.response.BaseResponseDto;
import RentalReminder.exception.ApiException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

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
            throw new ApiException("Access token missing", HttpStatus.UNAUTHORIZED);
        }
        return authService.verifyTokenAndGetUserId(accessToken);
    }

    // Ownership check
    protected void checkOwnership(UUID entityOwnerId, UUID currentUserId, String errorMsg) {
        if (!entityOwnerId.equals(currentUserId)) {
            throw new ApiException(errorMsg, HttpStatus.FORBIDDEN);
        }
    }

    // Fetch entity from Optional or throw
    protected <E> E getEntityById(Optional<E> optionalEntity, String notFoundMsg) {
        return optionalEntity.orElseThrow(() -> new ApiException(notFoundMsg, HttpStatus.NOT_FOUND));
    }

    // Create a response DTO instance
    protected <R extends BaseResponseDto> R createResponse(String message, Class<R> clazz) {
        try {
            R response = clazz.getDeclaredConstructor().newInstance();
            response.setMessage(message);
            return response;
        } catch (Exception e) {
            throw new ApiException("Failed to create response instance", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected void addCookie(BaseResponseDto response, ResponseCookie cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setHttpHeaders(headers);
    }

    // Helper to safely run repository operations
    protected <T> T safeRepo(Supplier<T> repoOperation, String genericErrorMessage) {
        try {
            return repoOperation.get();
        } catch (DataIntegrityViolationException e) {
            // Foreign key, unique constraint violations
            throw new ApiException("Operation failed due to data constraints: " + e.getMostSpecificCause().getMessage(),
                    HttpStatus.CONFLICT);
        } catch (DataAccessException e) {
            // General repository / database errors
            throw new ApiException(genericErrorMessage + ": " + e.getMostSpecificCause().getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Fallback for anything unexpected
            throw new ApiException(genericErrorMessage + ": " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected void safeRepoVoid(Runnable repoOperation, String genericErrorMessage) {
        safeRepo(() -> {
            repoOperation.run();
            return null;
        }, genericErrorMessage);
    }
}