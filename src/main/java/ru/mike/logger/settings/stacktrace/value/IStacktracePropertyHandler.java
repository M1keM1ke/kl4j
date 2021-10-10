package ru.mike.logger.settings.stacktrace.value;

import ru.mike.logger.settings.IPropertyHandler;
import ru.mike.logger.settings.stacktrace.StacktraceSetting;

public interface IStacktracePropertyHandler extends IPropertyHandler {
    void execute(StacktraceSetting setting, String propertyValue);

}
