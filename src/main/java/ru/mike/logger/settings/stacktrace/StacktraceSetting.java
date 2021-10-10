package ru.mike.logger.settings.stacktrace;


import org.reflections.Reflections;
import ru.mike.logger.AnsiColor;
import ru.mike.logger.Klogger;
import ru.mike.logger.settings.Setting;
import ru.mike.logger.settings.stacktrace.value.IStacktracePropertyHandler;
import ru.mike.reflection.KReflections;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StacktraceSetting extends Setting {
    private String name = "stacktrace.enable";
    private boolean stacktraceEnable;
    private static final String LOGGER_CLASS_NAME = Klogger.class.getName();
    private static final String METHOD_LOGGER_NAME = "log";
    private String color;

    private List<IStacktracePropertyHandler> stacktracePropertyHandlers;

    public StacktraceSetting() {
        stacktracePropertyHandlers = new Reflections("ru.mike.logger")
                .getSubTypesOf(IStacktracePropertyHandler.class)
                .stream()
                .map(clazz -> (IStacktracePropertyHandler) KReflections.newInstance(clazz))
                .collect(Collectors.toList())
        ;
    }

    public boolean isStacktraceEnable() {
        return stacktraceEnable;
    }

    public void setStacktraceEnable(boolean stacktraceEnable) {
        this.stacktraceEnable = stacktraceEnable;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String attend() {
        String stacktraceMessage = StackWalker
                .getInstance()
                .walk((Stream<StackWalker.StackFrame> frames) -> {
                    List<StackWalker.StackFrame> stacktraceList = frames.collect(Collectors.toList());
                    StackWalker.StackFrame initiallyCalledFrame = null;

                    for (int i = 0; i < stacktraceList.size(); i++) {
                        StackWalker.StackFrame preFrame = stacktraceList.get(i);
                        if (isLogMethodFrame(preFrame)) {
                            initiallyCalledFrame = stacktraceList.get(i + 1);
                            break;
                        }
                    }

                    if (initiallyCalledFrame == null) {
                        throw new RuntimeException("Unable to find called frame for stacktrace setting logger");
                    }

                    return String.format("%s.%s(%s:%s)",
                            initiallyCalledFrame.getClassName(), initiallyCalledFrame.getMethodName(),
                            initiallyCalledFrame.getFileName(), initiallyCalledFrame.getLineNumber());
                })
        ;

        return stacktraceEnable ? setUpLoggerStacktraceColors(stacktraceMessage) : "";
    }

    private String setUpLoggerStacktraceColors(String value) {
        return color + value + AnsiColor.RESET;
    }

    private boolean isLogMethodFrame(StackWalker.StackFrame preFrame) {
        return preFrame.getClassName().equals(LOGGER_CLASS_NAME) && preFrame.getMethodName().equals(METHOD_LOGGER_NAME);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setValue(String name, String value) {
        stacktracePropertyHandlers.stream()
                .filter(handler -> handler.isCurrentPropertyName(name))
                .findFirst()
                .ifPresent(handler -> handler.execute(this, value))
        ;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    public enum StacktraceProperty {
        STACKTRACE_ENABLE("stacktrace.enable"),
        STACKTRACE_PRIORITY("stacktrace.priority"),
        STACKTRACE_COLOR("stacktrace.color");

        private String name;

        StacktraceProperty(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
