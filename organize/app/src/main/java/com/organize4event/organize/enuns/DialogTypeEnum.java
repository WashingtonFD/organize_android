package com.organize4event.organize.enuns;

public enum DialogTypeEnum {
    JUSTPOSITIVE(1),
    POSITIVE_AND_NEGATIVE(2),
    CAMARA_AND_GALERY(3);

    private int value;

    DialogTypeEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }

}
