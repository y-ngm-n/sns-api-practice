package yapp.setting.api.twitter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitter4j.*;
import twitter4j.auth.OAuth2Authorization;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import yapp.setting.common.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class TwitterService {

    private final TwitterTokenRepository twitterTokenRepository;
    private final OAuth2TokenProvider twitterOAuth2TokenProvider;

    @Value("${oauth.twitter.client-id}")
    private String clientId;

    @Value("${oauth.twitter.client-secret}")
    private String clientSecret;

    @Value("${oauth.twitter.challenge}")
    private String challenge;

    private final String redirectUrl = "http://localhost:8080/x/success";
    private final String[] scopes = {"tweet.read", "tweet.write", "users.read", "offline.access"};
    private final Long userId = 1L;

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
    @Transactional
    public String getTwitterAuthorizationToken(String code) {
        OAuth2TokenProvider.Result result = twitterOAuth2TokenProvider.getAccessToken(clientId, redirectUrl, code, challenge);
        if (result != null) {
            System.out.println(result.getAccessToken());
            System.out.println(result.getRefreshToken());
            System.out.println(result.getScope());
            System.out.println(result.getExpiresIn());

            twitterTokenRepository.findById(userId)
                .map(t -> {
                    t.refresh(result.getAccessToken(), result.getRefreshToken());
                    return t;
                })
                .orElseGet(() -> new TwitterToken(result.getAccessToken(), result.getRefreshToken()));
        }

        return (result!=null) ? result.getAccessToken() : "failed" ;
    }

    /**
     * 트윗 생성 API 호출 메서드
     * @param content 트윗 내용
     */
    @Transactional
    public Long createTweet(String content) throws Exception {
        TwitterToken twitterToken = twitterTokenRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Twitter Token Not Found: " + userId));
        try {
            TwitterV2 twitterV2 = createTwitterV2(twitterToken.getAccessToken());
            CreateTweetResponse tweetResponse = twitterV2.createTweet(null, null, null, null, null, null, null, null, null, null, null, content);
            return tweetResponse.getId();
        }
        catch (TwitterException e) {
            if (e.getStatusCode() == 401) {
                refreshTwitterToken(twitterToken);
                TwitterV2 twitterV2 = createTwitterV2(twitterToken.getAccessToken());
                CreateTweetResponse tweetResponse = twitterV2.createTweet(null, null, null, null, null, null, null, null, null, null, null, content);
                return tweetResponse.getId();
            }
            throw e;
        }
    }

    /**
     * 사용자 정보 조회 API 호출 메서드
     */
    @Transactional
    public String getUser() throws TwitterException {
        TwitterToken twitterToken = twitterTokenRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Twitter Token Not Found: " + userId));
        try {
            TwitterV2 twitterV2 = createTwitterV2(twitterToken.getAccessToken());
            UsersResponse me = twitterV2.getMe("", null, "description");
            System.out.println(me);
            return me.toString();
        } catch (TwitterException e) {
            refreshTwitterToken(twitterToken);
            TwitterV2 twitterV2 = createTwitterV2(twitterToken.getAccessToken());
            UsersResponse me = twitterV2.getMe("", null, "description");
            System.out.println(me);
            return me.toString();
        }
    }

    /**
     * TwitterV2 클라이언트 인스턴스를 생성해 반환하는 메서드
     * @param accessToken accessToken
     * @return TwitterV2 인스턴스
     */
    @Transactional
    protected TwitterV2 createTwitterV2(String accessToken) {
        Configuration configuration = new ConfigurationBuilder()
            .setOAuthConsumerKey(clientId)
            .setOAuthConsumerSecret(clientSecret)
            .build();
        OAuth2Authorization auth = new OAuth2Authorization(configuration);
        auth.setOAuth2Token(new OAuth2Token("bearer", accessToken));

        Twitter twitter = new TwitterFactory(configuration).getInstance(auth);

        return TwitterV2ExKt.getV2(twitter);
    }

    /**
     * 토큰 만료 시 갱신 메서드
     * @param token 기존 TwitterToken 엔티티
     */
    @Transactional
    protected void refreshTwitterToken(TwitterToken token) throws RuntimeException {
        OAuth2TokenProvider.Result result = twitterOAuth2TokenProvider.refreshToken(clientId, token.getRefreshToken());
        if (result != null) {
            System.out.println(result.getAccessToken());
            System.out.println(result.getRefreshToken());
            token.refresh(result.getAccessToken(), result.getRefreshToken());
        } else throw new RuntimeException("토큰 갱신 실패");
    }
}
