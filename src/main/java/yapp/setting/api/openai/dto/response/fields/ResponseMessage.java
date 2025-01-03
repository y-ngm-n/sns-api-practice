package yapp.setting.api.openai.dto.response.fields;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

    private String role;

    private String content;

    private String refusal;
}
