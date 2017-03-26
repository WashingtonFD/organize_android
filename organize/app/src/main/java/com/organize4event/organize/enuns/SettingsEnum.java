package com.organize4event.organize.enuns;

public enum SettingsEnum {

    NOTIFICATIONS(1),
    PRIVACY(2),
    BEST_DAY_FOR_PAYMENT(3),
    ROUND_VALUES(4),
    EMAIL_REPORT(5),
    OUR_PLANS(6),
    TUTORIAL(7),
    EXIT(8);

    private int value;

    SettingsEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
