package com.organize4event.organize.enuns;

public enum AccessPlatformEnum {

    ANDROID(1),
    WEBSITE(2),
    IPHONE(3);

    private int value;

    AccessPlatformEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
