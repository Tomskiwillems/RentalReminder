package RentalReminder.dto.response;

import org.springframework.http.HttpHeaders;

public class BaseResponseDto {
    private String message;
    private HttpHeaders httpHeaders;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public HttpHeaders getHttpHeaders() { return httpHeaders; }
    public void setHttpHeaders(HttpHeaders httpHeaders) { this.httpHeaders = httpHeaders; }
}