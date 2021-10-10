package ru.mike.logger.settings.stacktrace.value;

import ru.mike.logger.settings.stacktrace.StacktraceSetting;

public class StacktracePriorityPropertyHandler implements IStacktracePropertyHandler {
    @Override
    public boolean isCurrentPropertyName(String propertyName) {
        return StacktraceSetting.StacktraceProperty.STACKTRACE_PRIORITY.getName().equals(propertyName);
    }

    @Override
    public void execute(StacktraceSetting setting, String propertyValue) {
        setting.setPriority(Integer.parseInt(propertyValue));
    }
}
