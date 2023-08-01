package art.evalevi.telegrambot.chatgptsimplebot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс для ответа от API GPT-3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private ChatRequest.Message message;
    }
}
