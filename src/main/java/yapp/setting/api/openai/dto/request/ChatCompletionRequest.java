package yapp.setting.api.openai.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yapp.setting.api.openai.dto.request.fields.RequestMessage;
import yapp.setting.post.dto.request.PostCreateRequest;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ChatCompletionRequest {

    private String model;
    private List<RequestMessage> messages;

    public static ChatCompletionRequest from(PostCreateRequest request, String model) {
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();

        ArrayList<RequestMessage> messages = new ArrayList<>();
        messages.add(new RequestMessage("developer", "너는 " + request.getRole() + "이야. 다음 규칙에 따라 게시물 내용을 생성해야 해.\n" + request.getCreatePrompt() + "\n게시물 내용은 다음 기사 내용을 바탕으로 생성해줘.\n" + request.getSource()));
        // messages.add(new RequestMessage("developer", "너는 " + request.getRole() + "이야. 다음 규칙에 따라 게시물 내용을 생성해야 해.\n" + request.getCreatePrompt()));
        // messages.add(new RequestMessage("developer", "게시물 내용은 다음 기사 내용을 바탕으로 생성해줘.\n" + request.getSource()));
        chatCompletionRequest.setMessages(messages);
        chatCompletionRequest.setModel(model);

        return chatCompletionRequest;
    }
}
