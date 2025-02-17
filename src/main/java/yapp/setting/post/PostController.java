package yapp.setting.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yapp.setting.api.rss.RssService;
import yapp.setting.api.rss.dto.RssFeedResponse;
import yapp.setting.post.dto.request.PostCreateRequest;
import yapp.setting.post.dto.request.PostEditRequest;
import yapp.setting.post.dto.response.PostCreateResponse;
import yapp.setting.post.dto.response.PostEditEachResponse;
import yapp.setting.post.dto.response.PostEditResponse;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final RssService rssService;

    @GetMapping
    public RssFeedResponse getRssFeed(@RequestParam String category) {
        return rssService.getFeed(category);
    }

    @PostMapping
    public PostCreateResponse createPost(@RequestBody PostCreateRequest postCreateRequest) {
        return postService.createPost(postCreateRequest);
    }

    @PutMapping
    public PostEditResponse editAllPosts(@RequestBody PostEditRequest postEditRequest) throws JsonProcessingException {
        return postService.editAllPosts(postEditRequest);
    }

    @PutMapping("/each")
    public PostEditEachResponse editEachPosts(@RequestBody PostEditRequest postEditRequest) {
        return postService.editEachPosts(postEditRequest);
    }
}
