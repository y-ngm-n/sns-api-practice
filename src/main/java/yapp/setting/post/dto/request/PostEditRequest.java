package yapp.setting.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yapp.setting.post.dto.PostList;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEditRequest {

    private String role;
    private String createPrompt;
    private String editPrompt;
    private List<PostList> posts;
}
