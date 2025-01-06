package yapp.setting.api.rss.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RssFeedItem {

    private String id;

    private String url;

    private String title;

    @JsonProperty("content_text")
    private String contentText;

    @JsonProperty("content_html")
    private String contentHtml;

    @JsonProperty("date_published")
    private String datePublished;
}
