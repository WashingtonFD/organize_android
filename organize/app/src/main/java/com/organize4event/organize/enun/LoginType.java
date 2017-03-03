package com.organize4event.organize.enun;


public enum LoginType {
    EMAIL(1),
    FACEBOOK(2),
    LINKEDIN(3),
    GOOGLE(4);


    private int value;

    LoginType(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
