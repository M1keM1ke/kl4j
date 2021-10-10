package ru.mike.logger.settings.level.value;

import ru.mike.logger.AnsiColor;
import ru.mike.logger.settings.Setting;
import ru.mike.logger.settings.level.LevelSetting;

public class LevelColorWarnPropertyHandler implements ILevelPropertyHandler {
    @Override
    public boolean isCurrentPropertyName(String propertyName) {
        return LevelSetting.LevelProperty.LEVEL_COLOR_WARN.getName().equals(propertyName);
    }

    @Override
    public void execute(LevelSetting setting, String propertyValue) {
        String colorByNameWarn = new AnsiColor()
                .findColorByName(propertyValue)
                .or(() -> new AnsiColor().findColorByValue(propertyValue))
                .orElseThrow(RuntimeException::new)
        ;

        setting.setWarnColor(colorByNameWarn);
    }
}
