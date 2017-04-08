package com.organize4event.organize.enuns;

public enum UserTypeEnum {

    MASTER(1),
    OWNER(2),
    ADMINISTRATOR(3),
    DEFAULT(4);

    private int value;

    UserTypeEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
