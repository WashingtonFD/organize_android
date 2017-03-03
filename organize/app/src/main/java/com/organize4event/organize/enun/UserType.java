package com.organize4event.organize.enun;


public enum UserType {
    MASTER(1),
    OWNER(2),
    ADMINISTRATOR(3),
    DEFAULT(4);


    private int value;

    UserType(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
