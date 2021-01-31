package com.xarql.util;

public abstract class NamedObject {
    private String name;

    public NamedObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
