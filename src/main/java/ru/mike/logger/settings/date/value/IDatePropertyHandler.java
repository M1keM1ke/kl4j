package ru.mike.logger.settings.date.value;

import ru.mike.logger.settings.date.DateSetting;
import ru.mike.logger.settings.IPropertyHandler;

public interface IDatePropertyHandler extends IPropertyHandler {
    void execute(DateSetting setting, String propertyValue);
}
