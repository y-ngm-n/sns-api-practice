package yapp.setting.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RssConfig {

    @Bean
    public Map<String, String> feeds() {
        return Map.of(
            "champs", "https://rss.app/feeds/tTzeUxjhVAVVqQqD.json",
            "ai", "https://rss.app/feeds/tyIHWR1Zlw4IYUfl.json"
        );
    }

    @Bean
    public RestClient rssFeedClient() {
        return RestClient.create();
    }

    @Bean
    public RestClient rssParserClient() {
        return RestClient.builder()
            .build();
    }
}
