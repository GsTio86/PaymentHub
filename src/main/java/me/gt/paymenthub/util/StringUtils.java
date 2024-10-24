package me.gt.paymenthub.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class StringUtils {

    public static String ecpayTradeInfoFormatJson(String source) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        String[] pairs = source.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";
            node.put(key, value);
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }

}
