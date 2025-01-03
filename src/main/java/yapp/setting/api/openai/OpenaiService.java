package yapp.setting.api.openai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenaiService {

    @Value("${openai.model}")
    private String model;
}
