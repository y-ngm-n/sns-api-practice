package yapp.setting.api.openai.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.lang.Nullable;
import yapp.setting.api.openai.dto.response.fields.Choice;
import yapp.setting.api.openai.dto.response.fields.Usage;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionResponse {

    private String id;

    private List<Choice> choices;

    private Integer created;

    private String model;

    @Nullable
    @JsonProperty("service_tier")
    private String serviceTier;

    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    private String object;

    private Usage usage;
}
