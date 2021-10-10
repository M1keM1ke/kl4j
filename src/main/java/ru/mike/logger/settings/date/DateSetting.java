package ru.mike.logger.settings.date;


import org.reflections.Reflections;
import ru.mike.logger.AnsiColor;
import ru.mike.logger.settings.Setting;
import ru.mike.logger.settings.date.value.IDatePropertyHandler;
import ru.mike.reflection.KReflections;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DateSetting extends Setting {
    private String name = "date.enable";
    private String datePattern;
    private String enable;
    private String color;

    private List<IDatePropertyHandler> datePropertyHandlers;

    public DateSetting() {
        datePropertyHandlers = new Reflections("ru.mike.logger")
                .getSubTypesOf(IDatePropertyHandler.class)
                .stream()
                .map(clazz -> (IDatePropertyHandler) KReflections.newInstance(clazz))
                .collect(Collectors.toList())
        ;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String attend() {
        boolean isDateEnable = Boolean.parseBoolean(enable);

        if (isDateEnable) {
            return setUpDateColor(new SimpleDateFormat(datePattern).format(new Date()));
        }

        return "";
    }

    private String setUpDateColor(String value) {
        return color + value + AnsiColor.RESET;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setValue(String name, String value) {
        datePropertyHandlers.stream()
                .filter(handler -> handler.isCurrentPropertyName(name))
                .findFirst()
                .ifPresent(handler -> handler.execute(this, value))
        ;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    public enum DateProperty {
        DATE_ENABLE("date.enable"),
        DATE_PATTERN("date.pattern"),
        DATE_PRIORITY("date.priority"),
        DATE_COLOR("date.color");

        private String name;

        DateProperty(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
