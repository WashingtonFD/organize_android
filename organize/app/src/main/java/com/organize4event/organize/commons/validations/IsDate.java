package com.organize4event.organize.commons.validations;

import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidateUsing(IsDateRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IsDate {
    boolean isRequired() default true;

    boolean past() default true;

    boolean future() default false;

    String emptyText() default "";

    int emptyTextResId() default -1;

    int sequence() default -1;

    int messageResId() default -1;

    String message() default "Invalid date";
}
