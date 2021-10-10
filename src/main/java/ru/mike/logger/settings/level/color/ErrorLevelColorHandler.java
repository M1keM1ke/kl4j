package ru.mike.logger.settings.level.color;

import ru.mike.logger.AnsiColor;
import ru.mike.logger.LogLevel;
import ru.mike.logger.settings.level.LevelSetting;

public class ErrorLevelColorHandler implements ILevelColorHandler {
    @Override
    public boolean isCurrentLoggerLevel(String levelName) {
        return LogLevel.ERROR.getName().equals(levelName);
    }

    @Override
    public String execute(LevelSetting levelSetting) {
        String errorColor = levelSetting.getErrorColor();
        String localLoggerLevelName = levelSetting.getLocalLevel().getName();
        String globalColor = levelSetting.getGlobalColor();

        return errorColor != null
                ? errorColor + localLoggerLevelName + AnsiColor.RESET : globalColor != null
                ? globalColor + localLoggerLevelName + AnsiColor.RESET : localLoggerLevelName;
    }
}
