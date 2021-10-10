package ru.mike.logger.settings.date.value;

import ru.mike.logger.settings.date.DateSetting;

public class DatePriorityPropertyHandler implements IDatePropertyHandler {
    @Override
    public boolean isCurrentPropertyName(String propertyName) {
        return DateSetting.DateProperty.DATE_PRIORITY.getName().equals(propertyName);
    }

    @Override
    public void execute(DateSetting setting, String propertyValue) {
        setting.setPriority(Integer.parseInt(propertyValue));
    }
}
