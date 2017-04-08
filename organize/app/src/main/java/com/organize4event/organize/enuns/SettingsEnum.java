package com.organize4event.organize.enuns;

public enum SettingsEnum {

    NOTIFICATIONS(1),
    PRIVACY(2),
    BEST_DAY_FOR_PAYMENT(3),
    ROUND_VALUES(4),
    EMAIL_REPORT(5),
    OUR_PLANS(6),
    TERM_USE(7),
    TUTORIAL(8),
    ABOUT(9),
    EXIT(10);

    private int value;

    SettingsEnum(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }
}
