package yapp.setting.twitter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/x")
@RequiredArgsConstructor
public class TwitterController {

    private final TwitterService twitterService;

    @GetMapping("/auth")
    public ResponseEntity<Object> getTwitterAuthorizationUrl() {
        String redirectUrl = twitterService.getTwitterAuthorizationUrl();
        System.out.println(redirectUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/success")
    public void getTwitterAuthorizationToken(
        @RequestParam String state,
        @RequestParam String code
    ) {
        String accessToken = twitterService.getTwitterAuthorizationToken(code);
        System.out.println(accessToken);
    }
}
