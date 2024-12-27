package yapp.setting.twitter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.OAuth2TokenProvider;

@Service
@RequiredArgsConstructor
public class TwitterService {

    private final OAuth2TokenProvider twitterOAuth2TokenProvider;

    @Value("${oauth.twitter.client-id}")
    private String clientId;

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
}
