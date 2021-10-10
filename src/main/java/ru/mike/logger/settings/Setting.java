package ru.mike.logger.settings;

public abstract class Setting {
    protected boolean mutable;
    protected String value;
    protected Integer priority;
    public abstract String attend();

    public abstract void setName(String name);

    public abstract String getName();

    public abstract void setValue(String name, String value);

    public abstract String getValue();

    public abstract void setPriority(Integer priority);

    public abstract Integer getPriority();
}
