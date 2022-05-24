package lbb.home.littlebigbot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@Getter
@ConfigurationProperties(prefix="parse")
public class ParseConfig {
    @Value("${parse.apiCall}")
    private String apiCall;
    @Value("${parse.apiKey}")
    private String apiKey;
    @Value("${parse.userAgent}")
    private String userAgent;
}
