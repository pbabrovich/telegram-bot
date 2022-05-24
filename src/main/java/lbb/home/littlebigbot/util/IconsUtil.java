package lbb.home.littlebigbot.util;

import java.util.HashMap;
import java.util.Map;

public class IconsUtil {
    public final static Map<String, String> weatherIcons = new HashMap<>();

    static {
        weatherIcons.put("Clear", "\uD83C\uDF28");
        weatherIcons.put("Rain", "\uD83D\uDCA7");
        weatherIcons.put("Snow", "\uD83C\uDF28");
        weatherIcons.put("Clouds", "\uD83C\uDF25");
    }
}