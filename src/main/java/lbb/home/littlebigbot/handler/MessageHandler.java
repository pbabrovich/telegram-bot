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
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        if (message.hasText()) {
            String text = message.getText();
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
        } else
            sendMessage.setText("Use /help to get information");

        return sendMessage;
    }
}
