package yapp.setting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.OAuth2TokenProvider;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitter API 사용에 필요한 Bean 등록
 */
@Configuration
public class TwitterConfig {

    @Value("${oauth.twitter.client-id}")
    private String clientId;

    @Value("${oauth.twitter.client-secret}")
    private String clientSecret;

    /**
     * twitter4j의 Configuration
     */
    @Bean
    public twitter4j.conf.Configuration twitterConfiguration() {
        return new ConfigurationBuilder()
            .setOAuthConsumerKey(clientId)
            .setOAuthConsumerSecret(clientSecret)
            .build();
    }

    /**
     * twitter4j의 OAuth2TokenProvider
     * twitter API 사용에 필요한 access token 발급받을 때 사용
     */
    @Bean
    public OAuth2TokenProvider twitterOAuth2TokenProvider(twitter4j.conf.Configuration twitterConfiguration) {
        return new OAuth2TokenProvider(twitterConfiguration);
    }
}
