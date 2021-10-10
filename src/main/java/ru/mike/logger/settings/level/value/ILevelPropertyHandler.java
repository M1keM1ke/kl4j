package ru.mike.logger.settings.level.value;

import ru.mike.logger.settings.IPropertyHandler;
import ru.mike.logger.settings.level.LevelSetting;

public interface ILevelPropertyHandler extends IPropertyHandler {
    void execute(LevelSetting setting, String propertyValue);
}
