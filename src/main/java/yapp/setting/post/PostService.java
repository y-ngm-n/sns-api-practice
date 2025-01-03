package yapp.setting.post;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yapp.setting.api.openai.OpenAiService;
import yapp.setting.api.openai.dto.request.ChatCompletionRequest;
import yapp.setting.api.openai.dto.response.ChatCompletionResponse;
import yapp.setting.post.dto.request.PostCreateRequest;
import yapp.setting.post.dto.request.PostEditRequest;
import yapp.setting.post.dto.response.PostCreateResponse;
import yapp.setting.post.dto.response.PostEditResponse;

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

//    public PostEditResponse editPosts(PostEditRequest postEditRequest) {
//
//    }
}
