package com.organize4event.organize.enuns;

public enum ContactTypeEnum {

    EMAIL(1),
    FACEBOOK(2),
    WHATSAPP(3),
    PHONE(4),
    CELLPHONE(5);

    private int value;

    ContactTypeEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
