package ru.mike.logger.settings.level.color;

import ru.mike.logger.settings.level.LevelSetting;

public interface ILevelColorHandler {
    boolean isCurrentLoggerLevel(String levelName);

    String execute(LevelSetting levelSetting);
}
