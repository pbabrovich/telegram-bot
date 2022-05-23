package lbb.home.littlebigbot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LittleBigBotConfig {
    @Value("${littlebigbot.webHookPath}")
    String webHookPath;
    @Value("${littlebigbot.botUserName}")
    String botUserName;
    @Value("${littlebigbot.botToken}")
    String botToken;

}
