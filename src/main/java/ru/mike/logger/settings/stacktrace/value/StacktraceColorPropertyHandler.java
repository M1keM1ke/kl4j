package ru.mike.logger.settings.stacktrace.value;

import ru.mike.logger.AnsiColor;
import ru.mike.logger.settings.stacktrace.StacktraceSetting;

import java.util.Optional;

public class StacktraceColorPropertyHandler implements IStacktracePropertyHandler {
    @Override
    public boolean isCurrentPropertyName(String propertyName) {
        return StacktraceSetting.StacktraceProperty.STACKTRACE_COLOR.getName().equals(propertyName);
    }

    @Override
    public void execute(StacktraceSetting setting, String propertyValue) {
        String colorByName = new AnsiColor()
                .findColorByName(propertyValue)
                .or(() -> new AnsiColor().findColorByValue(propertyValue))
                .orElseThrow(RuntimeException::new)
        ;

        setting.setColor(colorByName);
    }
}
