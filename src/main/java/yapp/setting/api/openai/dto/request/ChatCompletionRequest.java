package yapp.setting.api.openai.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yapp.setting.api.openai.dto.request.fields.RequestMessage;
import yapp.setting.post.dto.PostList;
import yapp.setting.post.dto.request.PostCreateRequest;
import yapp.setting.post.dto.request.PostEditRequest;

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
        messages.add(new RequestMessage("developer", "너는 " + request.getRole() + "이야. 다음 규칙에 따라 게시물 내용을 생성해야 해.\n" + request.getCreatePrompt()));
        messages.add(new RequestMessage("developer", "게시물 내용은 다음 기사 내용을 바탕으로 생성해줘.\n" + request.getSource()));
        chatCompletionRequest.setMessages(messages);
        chatCompletionRequest.setModel(model);

        return chatCompletionRequest;
    }

    public static ChatCompletionRequest from(PostEditRequest request, String model) throws JsonProcessingException {
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();

        ArrayList<RequestMessage> messages = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String contents = objectMapper.writeValueAsString(request.getPosts());

        messages.add(new RequestMessage("developer", "너는 " + request.getRole() + "이야. 다음 규칙에 따라 게시물 내용을 생성해야 해.\n" + request.getCreatePrompt()));
        messages.add(new RequestMessage("developer", "너가 만들어준 답변에 대해 다음과 같이 추가적으로 수정하고 싶어.\n" + request.getEditPrompt()));
        messages.add(new RequestMessage("developer", "이제 게시물을 입력받은 뒤, 리스트의 형태로 수정된 게시물들을 답변해줘. 다음 5개의 게시물을 json 형태로 알려줄게:\n" + contents));
        chatCompletionRequest.setMessages(messages);
        chatCompletionRequest.setModel(model);

        return chatCompletionRequest;
    }

    public static ChatCompletionRequest from(PostEditRequest request, String content, String model) {
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();

        ArrayList<RequestMessage> messages = new ArrayList<>();
        messages.add(new RequestMessage("developer", "너는 " + request.getRole() + "이야. 다음 규칙에 따라 게시물 내용을 생성해야 해.\n" + request.getCreatePrompt()));
        messages.add(new RequestMessage("developer", "너가 만들어준 답변에 대해 다음과 같이 추가적으로 수정하고 싶어.\n" + request.getEditPrompt()));
        messages.add(new RequestMessage("developer", "이제 게시물을 입력받은 뒤, 수정된 게시물을 답변해줘. 게시물을 알려줄게:\n" + content));
        chatCompletionRequest.setMessages(messages);
        chatCompletionRequest.setModel(model);

        return chatCompletionRequest;
    }
}
