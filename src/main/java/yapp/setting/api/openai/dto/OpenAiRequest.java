package yapp.setting.api.openai.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class OpenAiRequest {

    private String model;
    private List<OpenAiMessage> messages = new ArrayList<>();
}
