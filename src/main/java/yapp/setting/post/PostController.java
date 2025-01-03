package yapp.setting.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yapp.setting.post.dto.request.PostCreateRequest;
import yapp.setting.post.dto.request.PostEditRequest;
import yapp.setting.post.dto.response.PostCreateResponse;
import yapp.setting.post.dto.response.PostEditResponse;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostCreateResponse createPost(@ModelAttribute PostCreateRequest postCreateRequest) {
        return postService.createPost(postCreateRequest);
    }

//    @PutMapping
//    public PostEditResponse editPosts(PostEditRequest postEditRequest) {
//        return postService.editPosts(postEditRequest);
//    }
}
