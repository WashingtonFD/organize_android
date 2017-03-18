package com.organize4event.organize.enuns;

public enum AccessPlatformEnum {

    WEBSITE(1),
    ANDROID(2),
    IPHONE(3);

    private int value;

    AccessPlatformEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
