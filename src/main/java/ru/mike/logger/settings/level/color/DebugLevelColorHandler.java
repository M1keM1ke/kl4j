package ru.mike.logger.settings.level.color;

import ru.mike.logger.AnsiColor;
import ru.mike.logger.LogLevel;
import ru.mike.logger.settings.level.LevelSetting;

public class DebugLevelColorHandler implements ILevelColorHandler {
    @Override
    public boolean isCurrentLoggerLevel(String levelName) {
        return LogLevel.DEBUG.getName().equals(levelName);
    }

    @Override
    public String execute(LevelSetting levelSetting) {
        String debugColor = levelSetting.getDebugColor();
        String localLoggerLevelName = levelSetting.getLocalLevel().getName();
        String globalColor = levelSetting.getGlobalColor();

        return debugColor != null
                ? debugColor + localLoggerLevelName + AnsiColor.RESET : globalColor != null
                ? globalColor + localLoggerLevelName + AnsiColor.RESET : localLoggerLevelName;
    }
}
