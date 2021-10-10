package ru.mike.logger.settings.level.color;

import ru.mike.logger.AnsiColor;
import ru.mike.logger.LogLevel;
import ru.mike.logger.settings.level.LevelSetting;

public class WarnLevelColorHandler implements ILevelColorHandler {
    @Override
    public boolean isCurrentLoggerLevel(String levelName) {
        return LogLevel.WARN.getName().equals(levelName);
    }

    @Override
    public String execute(LevelSetting levelSetting) {
        String warnColor = levelSetting.getWarnColor();
        String localLoggerLevelName = levelSetting.getLocalLevel().getName();
        String globalColor = levelSetting.getGlobalColor();

        return warnColor != null
                ? warnColor + localLoggerLevelName + AnsiColor.RESET : globalColor != null
                ? globalColor + localLoggerLevelName + AnsiColor.RESET : localLoggerLevelName;
    }
}
