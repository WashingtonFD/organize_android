package com.organize4event.organize.enuns;

public enum AccessPlatform {

    WEBSITE(1),
    ANDROID(2),
    IPHONE(3);

    private int value;

    AccessPlatform(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
