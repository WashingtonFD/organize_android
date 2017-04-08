package com.organize4event.organize.enuns;

public enum LoginTypeEnum {

    EMAIL(1),
    FACEBOOK(2),
    LINKEDIN(3),
    GOOGLE(4);

    private int value;

    LoginTypeEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }

}
