package yapp.setting.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yapp.setting.api.openai.dto.response.ChatCompletionResponse;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateResponse {

    private String content;

    public static PostCreateResponse from(ChatCompletionResponse chatCompletionResponse) {
        PostCreateResponse response = new PostCreateResponse();
        response.setContent(chatCompletionResponse.getChoices().get(0).getMessage().getContent());
        return response;
    }
}
