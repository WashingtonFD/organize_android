package com.organize4event.organize.enuns;

public enum PlanEnum {

    FREE(1),
    BASIC(2),
    PREMIUM(3);

    private int value;

    PlanEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
