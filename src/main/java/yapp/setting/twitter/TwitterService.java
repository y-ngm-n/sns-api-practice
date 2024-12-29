package yapp.setting.twitter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.OAuth2Authorization;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@Service
@RequiredArgsConstructor
public class TwitterService {

    private final OAuth2TokenProvider twitterOAuth2TokenProvider;

    @Value("${oauth.twitter.client-id}")
    private String clientId;

    @Value("${oauth.twitter.client-secret}")
    private String clientSecret;

    @Value("${oauth.twitter.access-token}")
    private String accessToken;

    private final String redirectUrl = "http://localhost:8080/x/success";
    private final String[] scopes = {"tweet.read", "tweet.write", "users.read", "offline.access"};
    private final String challenge = "challenge";

    /**
     * authorization url 생성 메서드
     * @return authorization url
     */
    public String getTwitterAuthorizationUrl() {
        return twitterOAuth2TokenProvider.createAuthorizeUrl(clientId, redirectUrl, scopes, challenge);
    }

    /**
     * 발급받은 code를 가지고 access token을 발급받는 메서드
     * @param code 발급받은 code
     * @return access token
     */
    public String getTwitterAuthorizationToken(String code) {
        OAuth2TokenProvider.Result result = twitterOAuth2TokenProvider.getAccessToken(clientId, redirectUrl, code, challenge);
        if (result != null) {
            System.out.println(result.getAccessToken());
            System.out.println(result.getRefreshToken());
            System.out.println(result.getScope());
            System.out.println(result.getExpiresIn());
        }

        return (result!=null) ? result.getAccessToken() : "failed" ;
    }

    /**
     * 트윗 생성 API 호출 메서드
     * @param content 트윗 내용
     */
    public Long createTweet(String content) throws Exception {
        TwitterV2 twitterV2 = createTwitterV2(accessToken);

        CreateTweetResponse tweetResponse = twitterV2.createTweet(null, null, null, null, null, null, null, null, null, null, null, content);
        System.out.println(tweetResponse.getId());
        return tweetResponse.getId();
    }

    private TwitterV2 createTwitterV2(String accessToken) {
        Configuration configuration = new ConfigurationBuilder()
            .setOAuthConsumerKey(clientId)
            .setOAuthConsumerSecret(clientSecret)
            .build();
        OAuth2Authorization auth = new OAuth2Authorization(configuration);
        auth.setOAuth2Token(new OAuth2Token("bearer", accessToken));

        Twitter twitter = new TwitterFactory(configuration).getInstance(auth);
        System.out.println(twitter.getConfiguration());
        System.out.println(twitter.getAuthorization());

        return TwitterV2ExKt.getV2(twitter);
    }
}
