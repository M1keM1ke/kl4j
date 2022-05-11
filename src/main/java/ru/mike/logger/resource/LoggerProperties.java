package ru.mike.logger.resource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LoggerProperties {
    private static LoggerProperties loggerProperties = new LoggerProperties();
    private static List<PropertyPair> propertyPairs = new ArrayList<>();

    public static PropertyPair loggerLevelType = new PropertyPair("logger.level.type", "debug");
    public static PropertyPair loggerLevelPriority = new PropertyPair("logger.level.priority", "1");
//    public static PropertyPair loggerLevelColorGlobal = new PropertyPair("logger.level.color.global", "GREEN");
    public static PropertyPair loggerLevelColorDebug = new PropertyPair("logger.level.color.debug", "CYAN");
    public static PropertyPair loggerLevelColorInfo = new PropertyPair("logger.level.color.info", "GREEN");
    public static PropertyPair loggerLevelColorWarn = new PropertyPair("logger.level.color.warn", "YELLOW");
    public static PropertyPair loggerLevelColorError = new PropertyPair("logger.level.color.error", "RED");
    public static PropertyPair loggerStacktraceEnable = new PropertyPair("logger.stacktrace.enable", "true");
    public static PropertyPair loggerStacktracePriority = new PropertyPair("logger.stacktrace.priority", "2");
    public static PropertyPair loggerStacktraceColor = new PropertyPair("logger.stacktrace.color", "BLUE");
    public static PropertyPair loggerDateEnable = new PropertyPair("logger.date.enable", "true");
    public static PropertyPair loggerDatePattern = new PropertyPair("logger.date.pattern", "yyyy.MM.dd'T'hh.mm.ss");
    public static PropertyPair loggerDatePriority = new PropertyPair("logger.date.priority", "3");
    public static PropertyPair loggerDateColor = new PropertyPair("logger.date.color", "PURPLE");

    public static List<PropertyPair> getProperties() throws IllegalAccessException {
        for (Field field : LoggerProperties.class.getDeclaredFields()) {
            Object value = field.get(loggerProperties);

            if (value instanceof PropertyPair) {
                propertyPairs.add((PropertyPair) value);
            }
        }

        return propertyPairs;
    }
}
