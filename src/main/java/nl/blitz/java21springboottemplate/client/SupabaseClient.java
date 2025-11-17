package nl.blitz.java21springboottemplate.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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
        return restClient.post()
                .uri("/auth/v1/signup")
                .header("Authorization", "Bearer " + anonKey)
                .body(requestBody)
                .retrieve()
                .body(responseType);
    }

    public <T, R> R login(T requestBody, Class<R> responseType) {
        return restClient.post()
                .uri("/auth/v1/token")
                .header("Authorization", "Bearer " + anonKey)
                .body(requestBody)
                .retrieve()
                .body(responseType);
    }
}
