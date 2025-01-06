package yapp.setting.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yapp.setting.api.openai.OpenAiService;
import yapp.setting.api.openai.dto.request.ChatCompletionRequest;
import yapp.setting.api.openai.dto.response.ChatCompletionResponse;
import yapp.setting.api.rss.RssService;
import yapp.setting.post.dto.request.PostCreateRequest;
import yapp.setting.post.dto.request.PostEditRequest;
import yapp.setting.post.dto.response.PostCreateResponse;
import yapp.setting.post.dto.response.PostEditEachResponse;
import yapp.setting.post.dto.response.PostEditResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    @Value("${openai.model}")
    private String openAiModel;

    private final OpenAiService openAiService;

    public PostCreateResponse createPost(PostCreateRequest postCreateRequest) {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.from(postCreateRequest, openAiModel);
        ChatCompletionResponse chatCompletionResponse = openAiService.getChatCompletion(chatCompletionRequest);
        return PostCreateResponse.from(chatCompletionResponse);
    }

    public PostEditResponse editAllPosts(PostEditRequest postEditRequest) throws JsonProcessingException {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.from(postEditRequest, openAiModel);
        ChatCompletionResponse chatCompletionResponse = openAiService.getChatCompletion(chatCompletionRequest);
        return PostEditResponse.from(chatCompletionResponse);
    }

    public PostEditEachResponse editEachPosts(PostEditRequest postEditRequest) {
        List<CompletableFuture<ChatCompletionResponse>> results = postEditRequest.getPosts().stream()
            .map(post -> {
                ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.from(postEditRequest, post.getContent(), openAiModel);
                return openAiService.getChatCompletionAsync(chatCompletionRequest);
            })
            .toList();

        List<ChatCompletionResponse> responses = CompletableFuture.allOf(results.toArray(new CompletableFuture[0]))
            .thenApply(Void -> results.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()))
            .join();

        return PostEditEachResponse.from(responses);
    }
}
