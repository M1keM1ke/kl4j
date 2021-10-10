package ru.mike.logger.settings.level.value;

import ru.mike.logger.LogLevel;
import ru.mike.logger.settings.Setting;
import ru.mike.logger.settings.level.LevelSetting;

public class LevelTypePropertyHandler implements ILevelPropertyHandler {
    @Override
    public boolean isCurrentPropertyName(String propertyName) {
        return LevelSetting.LevelProperty.LEVEL_TYPE.getName().equals(propertyName);
    }

    @Override
    public void execute(LevelSetting setting, String propertyValue) {
        setting.setGlobalLevel(LogLevel.findLevel(propertyValue));
    }
}
