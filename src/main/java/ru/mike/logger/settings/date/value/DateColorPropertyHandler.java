package ru.mike.logger.settings.date.value;

import ru.mike.logger.AnsiColor;
import ru.mike.logger.settings.date.DateSetting;

public class DateColorPropertyHandler implements IDatePropertyHandler {
    @Override
    public boolean isCurrentPropertyName(String propertyName) {
        return DateSetting.DateProperty.DATE_COLOR.getName().equals(propertyName);
    }

    @Override
    public void execute(DateSetting setting, String propertyValue) {
        String colorByName = new AnsiColor()
                .findColorByName(propertyValue)
                .or(() -> new AnsiColor().findColorByValue(propertyValue))
                .orElseThrow(RuntimeException::new)
        ;

        setting.setColor(colorByName);
    }
}
