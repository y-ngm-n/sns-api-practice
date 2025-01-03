package yapp.setting.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api-url}")
    private String url;

    @Value("${openai.api-key}")
    private String key;

    @Bean
    public RestClient openAiClient() {
        return RestClient.builder()
            .baseUrl(url)
            .defaultHeader("Authorization", "Bearer " + key)
            .build();
    }
}
