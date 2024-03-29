package art.evalevi.telegrambot.chatgptsimplebot.config;

import art.evalevi.telegrambot.chatgptsimplebot.bot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

    /**
     * Создание бина для регистрации бота в Telegram
     * @param telegramBot бот
     * @return объект для регистрации бота в Telegram
     * @throws TelegramApiException исключение при регистрации бота
     */
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBot);
        return botsApi;
    }
}

