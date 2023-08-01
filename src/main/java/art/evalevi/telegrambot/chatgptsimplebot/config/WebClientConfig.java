package art.evalevi.telegrambot.chatgptsimplebot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Конфигурация клиента для взаимодействия с OpenAI API
 */
@Configuration
public class WebClientConfig {

    @Value("${openai.api-key}")
    private String apiKey;

    /**
     * Создание бина для взаимодействия с OpenAI API
     * @return клиент для взаимодействия с OpenAI API
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }
}
