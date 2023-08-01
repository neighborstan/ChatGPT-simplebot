package art.evalevi.telegrambot.chatgptsimplebot.service;

import art.evalevi.telegrambot.chatgptsimplebot.dto.ChatRequest;
import art.evalevi.telegrambot.chatgptsimplebot.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Сервис для взаимодействия с OpenAI API
 */
@Service
public class ChatGptService {

    private final WebClient webClient;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api-url}")
    private String apiUrl;

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Генерация ответа на сообщение пользователя
     * @param prompt сообщение пользователя
     * @return ответ на сообщение пользователя
     */
    public String generateMessage(String prompt) {
        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse response;

        try {
            // Задержка между запросами, например, 1000 миллисекунд (1 секунда)
            Thread.sleep(2000);

            response = webClient.post()
                    .uri(apiUrl)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();
        } catch (InterruptedException e) {
            // Обработка исключения, если возникла проблема с задержкой
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
