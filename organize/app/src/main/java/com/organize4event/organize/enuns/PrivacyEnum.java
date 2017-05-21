package com.organize4event.organize.enuns;

public enum PrivacyEnum {

    ALL(1),
    JUST_FRIENDS(2),
    NO_ONE(3);

    private int value;

    PrivacyEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
