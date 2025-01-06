package yapp.setting.api.rss;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import yapp.setting.api.rss.dto.RssFeedResponse;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RssService {

    private final Map<String, String> feeds;
    private final RestClient rssFeedClient;
    private final RestClient rssParserClient;

    private String convertCategoryToFeedUrl(String category) {
        return feeds.get(category);
    }

    public RssFeedResponse getFeed(String category) {
        String feedUrl = convertCategoryToFeedUrl(category);
        return rssFeedClient.get()
            .uri(feedUrl)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                throw new RuntimeException("Client error: " + res.getStatusCode());
            })
            .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                throw new RuntimeException("Server error: " + res.getStatusCode());
            })
            .body(RssFeedResponse.class);
    }
}
