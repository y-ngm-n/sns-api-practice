package yapp.setting.api.openai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import yapp.setting.api.openai.dto.request.ChatCompletionRequest;
import yapp.setting.api.openai.dto.response.ChatCompletionResponse;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final RestClient openAiClient;

    public ChatCompletionResponse getChatCompletion(ChatCompletionRequest chatCompletionRequest) {
        return openAiClient.post()
            .body(chatCompletionRequest)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                throw new RuntimeException("Client error: " + res.getStatusCode());
            })
            .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                throw new RuntimeException("Server error: " + res.getStatusCode());
            })
            .body(ChatCompletionResponse.class);
    }

    @Async
    public CompletableFuture<ChatCompletionResponse> getChatCompletionAsync(ChatCompletionRequest chatCompletionRequest) {
        return CompletableFuture.supplyAsync(() -> {
            return openAiClient.post()
                .body(chatCompletionRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new RuntimeException("Client error: " + res.getStatusCode());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RuntimeException("Server error: " + res.getStatusCode());
                })
                .body(ChatCompletionResponse.class);
        });
    }
}
