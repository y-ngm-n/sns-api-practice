package yapp.setting.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yapp.setting.api.openai.dto.response.ChatCompletionResponse;
import yapp.setting.post.dto.PostList;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEditEachResponse {

    private List<PostList> posts = new ArrayList<>();

    public static PostEditEachResponse from(List<ChatCompletionResponse> answers) {
        PostEditEachResponse response = new PostEditEachResponse();
        for (ChatCompletionResponse answer : answers) {
            PostList post = new PostList(answers.indexOf(answer)+1, answer.getChoices().get(0).getMessage().getContent());
            response.getPosts().add(post);
        }
        return response;
    }
}
