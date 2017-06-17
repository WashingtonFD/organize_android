package com.organize4event.organize.commons.validations;

import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidateUsing(CpfCnpjRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CpfCnpj {
    boolean isRequired() default true;

    String emptyText() default "";

    int emptyTextResId() default -1;

    int sequence() default -1;

    int messageResId() default -1;

    String message() default "Invalid Cpf/Cnpj";
}