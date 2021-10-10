package ru.mike.logger.settings.level;

import org.reflections.Reflections;
import ru.mike.logger.AnsiColor;
import ru.mike.logger.LogLevel;
import ru.mike.logger.settings.Setting;
import ru.mike.logger.settings.level.color.ILevelColorHandler;
import ru.mike.logger.settings.level.value.ILevelPropertyHandler;
import ru.mike.reflection.KReflections;

import java.util.List;
import java.util.stream.Collectors;

public class LevelSetting extends Setting {
    private String name = "level";
    private LogLevel globalLevel;
    private LogLevel localLevel;
    private String globalColor;
    private String debugColor;
    private String infoColor;
    private String warnColor;
    private String errorColor;

    List<ILevelColorHandler> levelColorHandlers;
    List<ILevelPropertyHandler> levelPropertyHandlers;

    public LevelSetting() {
        Reflections reflections = new Reflections("ru.mike.logger");

        levelColorHandlers = reflections
                .getSubTypesOf(ILevelColorHandler.class)
                .stream()
                .map(clazz -> (ILevelColorHandler) KReflections.newInstance(clazz))
                .collect(Collectors.toList())
        ;

        levelPropertyHandlers = reflections
                .getSubTypesOf(ILevelPropertyHandler.class)
                .stream()
                .map(clazz -> (ILevelPropertyHandler) KReflections.newInstance(clazz))
                .collect(Collectors.toList())
        ;

    }

    public void setGlobalLevel(LogLevel globalLevel) {
        this.globalLevel = globalLevel;
    }

    public LogLevel getLocalLevel() {
        return localLevel;
    }

    public void setLocalLevel(LogLevel localLevel) {
        this.localLevel = localLevel;
    }

    public String getGlobalColor() {
        return globalColor;
    }

    public void setGlobalColor(String globalColor) {
        this.globalColor = globalColor;
    }

    public String getDebugColor() {
        return debugColor;
    }

    public void setDebugColor(String debugColor) {
        this.debugColor = debugColor;
    }

    public String getInfoColor() {
        return infoColor;
    }

    public void setInfoColor(String infoColor) {
        this.infoColor = infoColor;
    }

    public String getWarnColor() {
        return warnColor;
    }

    public void setWarnColor(String warnColor) {
        this.warnColor = warnColor;
    }

    public String getErrorColor() {
        return errorColor;
    }

    public void setErrorColor(String errorColor) {
        this.errorColor = errorColor;
    }

    @Override
    public String attend() {
        if (localLevel.getValue() < globalLevel.getValue()) {
            throw new RuntimeException();
        }
        return setUpLoggerLevelColors();
    }

    private String setUpLoggerLevelColors() {
        if (globalColor != null) {
            return globalColor + localLevel.getName() + AnsiColor.RESET;
        }

        return levelColorHandlers.stream()
                .filter(handler -> handler.isCurrentLoggerLevel(localLevel.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        String.format("Unsupported localLevel :%s. Unable to find handler for it", localLevel.getName()))
                )
                .execute(this)
        ;
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
        levelPropertyHandlers.stream()
                .filter(handler -> handler.isCurrentPropertyName(name))
                .findFirst()
                .ifPresent(handler -> handler.execute(this, value))
        ;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    public enum LevelProperty {
        LEVEL_TYPE("level.type"),
        LEVEL_PRIORITY("level.priority"),
        LEVEL_COLOR_GLOBAL("level.color.global"),
        LEVEL_COLOR_DEBUG("level.color.debug"),
        LEVEL_COLOR_INFO("level.color.info"),
        LEVEL_COLOR_WARN("level.color.warn"),
        LEVEL_COLOR_ERROR("level.color.error");

        private String name;

        LevelProperty(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
