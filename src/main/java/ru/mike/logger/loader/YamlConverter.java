package ru.mike.logger.loader;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class YamlConverter {
    private static Properties properties = new Properties();

    public static Properties toProperties(TreeMap<String, Map<String, Object>> config) {
        for (String key : config.keySet()) {
            toString(key, config.get(key));
        }

        return properties;
    }

    private static void toString(String key, Object mapr) {
        Map<String, Object> map = (Map<String, Object>) mapr;

        for (String mapKey : map.keySet()) {
            if (map.get(mapKey) instanceof Map) {
                toString(key + "." + mapKey, map.get(mapKey));
            } else {
                properties.put(String.format("%s.%s", key, mapKey), map.get(mapKey));
            }
        }
    }
}