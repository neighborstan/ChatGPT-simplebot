package art.evalevi.telegrambot.chatgptsimplebot.bot;

import art.evalevi.telegrambot.chatgptsimplebot.service.ChatGptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final ChatGptService chatGptService;

    public TelegramBot(ChatGptService chatGptService, @Value("${telegram.bot.token}") String token) {
        super(new DefaultBotOptions(), token);

        this.chatGptService = chatGptService;

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            // Отправить сообщение о том, что ответ формируется
            SendMessage waitMessage = new SendMessage();
            waitMessage.setChatId(chatId);
            waitMessage.setText("Подождите, формируется ответ...");

            try {
                Message sentWaitMessage = execute(waitMessage); // Сохраняем сообщение, чтобы потом удалить его

                // Получить ответ от ChatGptService
                String reply = chatGptService.generateMessage(messageText);

                // Удалить сообщение о том, что ответ формируется
                DeleteMessage deleteWaitMessage = new DeleteMessage();
                deleteWaitMessage.setChatId(chatId);
                deleteWaitMessage.setMessageId(sentWaitMessage.getMessageId());
                execute(deleteWaitMessage);

                // Отправить сформированный ответ пользователю
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText(reply);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
