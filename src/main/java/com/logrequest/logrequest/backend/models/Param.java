package com.logrequest.logrequest.backend.models;

public class Param extends Request {

    private String key;
    private String value;

    public Param(String key, String value) {
        this.setName(key).setValue(value);
    }

    public String getName() {
        return key;
    }

    public Param setName(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Param setValue(String value) {
        this.value = value;
        return this;
    }
}
