package lbb.home.littlebigbot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class LittleBigBotConfig {
    @Value("${littlebigbot.webHookPath}")
    private String webHookPath;
    @Value("${littlebigbot.botUserName}")
    private String botUserName;
    @Value("${littlebigbot.botToken}")
    private String botToken;

}
