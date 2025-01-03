package yapp.setting.api.openai.dto.response.fields;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Choice {

    private Integer index;

    private ResponseMessage message;

    @JsonProperty("finish_reason")
    private String finishReason;
}
