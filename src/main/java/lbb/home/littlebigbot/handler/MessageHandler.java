package lbb.home.littlebigbot.handler;

import lbb.home.littlebigbot.parser.WeatherParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler {
    private final WeatherParser weatherParser;

    public MessageHandler(WeatherParser weatherParser) {
        this.weatherParser = weatherParser;
    }
    public BotApiMethod<?> handle(Message message) {
        long chatId = message.getChatId();
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        switch (text) {
            case "/start":
                sendMessage.setText("Input city name to get the weather.");
                break;
            case "/help":
                sendMessage.setText("City name format: Wroclaw / London");
                break;
            default:
                sendMessage.setText(weatherParser.parseForecast(text));
                break;

        }
        return sendMessage;
    }
}
