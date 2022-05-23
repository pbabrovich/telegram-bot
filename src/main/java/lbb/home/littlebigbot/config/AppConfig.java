package lbb.home.littlebigbot.config;

import lbb.home.littlebigbot.model.LittleBigBot;
import lbb.home.littlebigbot.model.TelegramFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class AppConfig {
    private final LittleBigBotConfig littleBigBotConfig;


    public AppConfig(LittleBigBotConfig littleBigBotConfig) {
        this.littleBigBotConfig = littleBigBotConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(littleBigBotConfig.getWebHookPath()).build();
    }

    @Bean
    public LittleBigBot webHookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        LittleBigBot littleBigBot = new LittleBigBot(telegramFacade, setWebhook);
        littleBigBot.setBotToken(littleBigBotConfig.getBotToken());
        littleBigBot.setWebHookPath(littleBigBotConfig.getWebHookPath());



        return  littleBigBot;
    }
}
