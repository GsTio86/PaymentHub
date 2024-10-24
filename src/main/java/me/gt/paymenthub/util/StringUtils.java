package me.gt.paymenthub.util;


import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class StringUtils {

    public static String ecpayTradeInfoFormatJson(String source) throws JSONException {
        JSONObject json = new JSONObject();
        String[] pairs = source.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";
            json.put(key, value);
        }
        return json.toString(4);
    }

}
