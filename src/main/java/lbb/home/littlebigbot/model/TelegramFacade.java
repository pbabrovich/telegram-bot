package lbb.home.littlebigbot.model;

import lbb.home.littlebigbot.handler.MessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramFacade {

    private final MessageHandler messageHandler;

    public TelegramFacade(MessageHandler messageHandler1) {
        this.messageHandler = messageHandler1;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return null;
        } else {
            Message message = update.getMessage();
            return messageHandler.handle(message);
            }
    }
}
