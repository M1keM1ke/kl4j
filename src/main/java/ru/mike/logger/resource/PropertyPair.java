package ru.mike.logger.resource;

public class PropertyPair {
    private String name;
    private Object value;


    public PropertyPair(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
