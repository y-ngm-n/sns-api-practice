package yapp.setting.api.rss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RssFeedResponse {

    private String id;

    private String title;

    private String description;

    private String generator;

    private String feedUrl;

    private String siteUrl;

    private String imageUrl;

    private List<RssFeedItem> items;

    @Override
    public String toString() {
        return id + "\n" +
            title + "\n" +
            description + "\n" +
            generator + "\n" +
            feedUrl + "\n" +
            siteUrl + "\n" +
            imageUrl + "\n";
    }
}
