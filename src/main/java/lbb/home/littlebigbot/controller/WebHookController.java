package lbb.home.littlebigbot.controller;

import lbb.home.littlebigbot.model.LittleBigBot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

    private final LittleBigBot littleBigBot;

    public WebHookController(LittleBigBot littleBigBot) {
        this.littleBigBot = littleBigBot;
    }

    @PostMapping( "/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return littleBigBot.onWebhookUpdateReceived(update);
    }

}

