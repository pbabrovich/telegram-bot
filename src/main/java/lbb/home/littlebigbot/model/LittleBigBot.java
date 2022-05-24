package lbb.home.littlebigbot.model;

import lbb.home.littlebigbot.parser.WeatherParser;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
public class LittleBigBot extends SpringWebhookBot {
    private String webHookPath;
    private String botToken;
    private String botUserName;

    private TelegramFacade telegramFacade;
    private WeatherParser weatherParser;

    public LittleBigBot(TelegramFacade telegramFacade, DefaultBotOptions botOptions, SetWebhook setWebhook) {
        super(botOptions, setWebhook);
        this.telegramFacade = telegramFacade;
    }

    public LittleBigBot(TelegramFacade telegramFacade, SetWebhook setWebhook) {
        super(setWebhook);
        this.telegramFacade = telegramFacade;
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
}
