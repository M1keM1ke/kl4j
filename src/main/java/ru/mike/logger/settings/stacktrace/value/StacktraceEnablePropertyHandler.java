package ru.mike.logger.settings.stacktrace.value;

import ru.mike.logger.settings.stacktrace.StacktraceSetting;

public class StacktraceEnablePropertyHandler implements IStacktracePropertyHandler {
    @Override
    public boolean isCurrentPropertyName(String propertyName) {
        return StacktraceSetting.StacktraceProperty.STACKTRACE_ENABLE.getName().equals(propertyName);
    }

    @Override
    public void execute(StacktraceSetting setting, String propertyValue) {
        setting.setStacktraceEnable(Boolean.parseBoolean(propertyValue));
    }
}
