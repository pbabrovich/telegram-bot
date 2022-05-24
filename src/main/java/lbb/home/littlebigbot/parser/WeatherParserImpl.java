package lbb.home.littlebigbot.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lbb.home.littlebigbot.config.ParseConfig;
import lbb.home.littlebigbot.util.IconsUtil;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class WeatherParserImpl implements WeatherParser {
    private final DateTimeFormatter DATE_INPUT_FORMAT = DateTimeFormatter.
            ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter DATE_OUTPUT_FORMAT = DateTimeFormatter.
            ofPattern("MMM-dd HH:mm", Locale.US);
    private final ParseConfig parseConfig;

    public WeatherParserImpl(ParseConfig parseConfig) {
        this.parseConfig = parseConfig;
    }


    @Override
    public String parseForecast(String city) {
        String result;
        try {
            String jsonData = getJsonData(city);
            List<String> linesOfForecast = convertData(jsonData);
            result = String.format("%s:%s%s", city, System.lineSeparator(), parseDataFromList(linesOfForecast));
        } catch (IllegalArgumentException e) {
            return String.format("There is no such city \"%s\". Try again.", city);
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong";
        }
        return result;
    }


    private String getJsonData(String city) throws Exception {
        String urlString = parseConfig.getApiCall() + city + parseConfig.getApiKey();
        URL urlObject = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", parseConfig.getUserAgent());

        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            throw new IllegalArgumentException();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }


    private List<String> convertData(String data) throws Exception {
        List<String> weatherList = new ArrayList<>();

        JsonNode arrNode = new ObjectMapper().readTree(data).get("list");
        if (arrNode.isArray()) {
            for (final JsonNode objNode : arrNode) {
                String forecastTime = objNode.get("dt_txt").toString();
                if (forecastTime.contains("09:00") || forecastTime.contains("18:00")) {
                    weatherList.add(objNode.toString());
                }
            }
        }
        return weatherList;
    }

    private String parseDataFromList(List<String> weatherList) {
        final StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String line : weatherList) {
            {
                String dateTime;
                JsonNode mainNode;
                JsonNode weatherArrNode;
                try {
                    mainNode = objectMapper.readTree(line).get("main");
                    weatherArrNode = objectMapper.readTree(line).get("weather");
                    for (final JsonNode objNode : weatherArrNode) {
                        dateTime = objectMapper.readTree(line).get("dt_txt").toString();
                        sb.append(formatOutputData(dateTime, objNode.get("main").toString(), mainNode.get("temp").asDouble()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private String formatOutputData(String dateTime, String description, double temperature) {
        LocalDateTime forecastDateTime = LocalDateTime.parse(dateTime.replaceAll("\"", ""), DATE_INPUT_FORMAT);
        String formattedDateTime = forecastDateTime.format(DATE_OUTPUT_FORMAT);

        String formattedTemperature;
        long roundedTemperature = Math.round(temperature);
        if (roundedTemperature > 0) {
            formattedTemperature = "+" + Math.round(temperature);
        } else {
            formattedTemperature = String.valueOf(Math.round(temperature));
        }

        String formattedDescription = description.replaceAll("\"", "");

        String weatherIconCode = IconsUtil.weatherIcons.get(formattedDescription);

        return String.format("%s   %s %s %s%s", formattedDateTime, formattedTemperature, formattedDescription, weatherIconCode, System.lineSeparator());
    }
}
