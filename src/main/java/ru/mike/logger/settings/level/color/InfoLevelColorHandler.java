package ru.mike.logger.settings.level.color;

import ru.mike.logger.AnsiColor;
import ru.mike.logger.LogLevel;
import ru.mike.logger.settings.level.LevelSetting;

public class InfoLevelColorHandler implements ILevelColorHandler {
    @Override
    public boolean isCurrentLoggerLevel(String levelName) {
        return LogLevel.INFO.getName().equals(levelName);
    }

    @Override
    public String execute(LevelSetting levelSetting) {
        String infoColor = levelSetting.getInfoColor();
        String localLoggerLevelName = levelSetting.getLocalLevel().getName();
        String globalColor = levelSetting.getGlobalColor();

        return infoColor != null
                ? infoColor + localLoggerLevelName + AnsiColor.RESET : globalColor != null
                ? globalColor + localLoggerLevelName + AnsiColor.RESET : localLoggerLevelName;
    }
}
