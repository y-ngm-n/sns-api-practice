package yapp.setting.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yapp.setting.api.openai.dto.response.ChatCompletionResponse;
import yapp.setting.post.dto.PostList;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEditResponse {

    private String content;

    public static PostEditResponse from(ChatCompletionResponse chatCompletionResponse) {
        PostEditResponse response = new PostEditResponse();
        response.setContent(chatCompletionResponse.getChoices().get(0).getMessage().getContent());
        return response;
    }
}
