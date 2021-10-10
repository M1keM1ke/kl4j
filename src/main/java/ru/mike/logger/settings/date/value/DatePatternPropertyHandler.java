package ru.mike.logger.settings.date.value;

import ru.mike.logger.settings.date.DateSetting;

public class DatePatternPropertyHandler implements IDatePropertyHandler {
    @Override
    public boolean isCurrentPropertyName(String propertyName) {
        return DateSetting.DateProperty.DATE_PATTERN.getName().equals(propertyName);
    }

    @Override
    public void execute(DateSetting setting, String propertyValue) {
        setting.setDatePattern(propertyValue);
    }
}
