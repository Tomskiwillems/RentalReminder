package RentalReminder.client;

import RentalReminder.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class SupabaseClient {

    private final RestClient restClient;
    private final String anonKey;

    public SupabaseClient(
            RestClient.Builder restClientBuilder,
            @Value("${supabase.url}") String supabaseUrl,
            @Value("${supabase.anon-key}") String anonKey) {
        this.anonKey = anonKey;
        this.restClient = restClientBuilder
                .baseUrl(supabaseUrl)
                .defaultHeader("apikey", anonKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public <T, R> R register(T requestBody, Class<R> responseType) {
        try {
            return restClient.post()
                    .uri("/auth/v1/signup")
                    .header("Authorization", "Bearer " + anonKey)
                    .body(requestBody)
                    .retrieve()
                    .body(responseType);
        } catch (HttpClientErrorException e) {
            HttpStatus status = HttpStatus.resolve(e.getStatusCode().value());
            throw new ApiException(
                    "Failed to register user: " + e.getResponseBodyAsString(),
                    status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            throw new ApiException("Unexpected error during registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public <T, R> R login(T requestBody, Class<R> responseType) {
        try {
            return restClient.post()
                    .uri("/auth/v1/token?grant_type=password")
                    .header("Authorization", "Bearer " + anonKey)
                    .body(requestBody)
                    .retrieve()
                    .body(responseType);
        } catch (HttpClientErrorException e) {
            HttpStatus status = HttpStatus.resolve(e.getStatusCode().value());
            if (e.getStatusCode() == org.springframework.http.HttpStatus.BAD_REQUEST) {
                throw new ApiException("Invalid email or password", HttpStatus.BAD_REQUEST);
            }
            throw new ApiException(
                    "Failed to login user: " + e.getResponseBodyAsString(),
                    status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {
            throw new ApiException("Unexpected error during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
