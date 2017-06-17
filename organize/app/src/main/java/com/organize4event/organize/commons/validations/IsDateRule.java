package com.organize4event.organize.commons.validations;

import com.mobsandgeeks.saripaar.ContextualAnnotationRule;
import com.mobsandgeeks.saripaar.ValidationContext;
import com.organize4event.organize.commons.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsDateRule extends ContextualAnnotationRule<IsDate, String> {

    protected IsDateRule(ValidationContext validationContext, IsDate isDate) {
        super(validationContext, isDate);
    }

    @Override
    public boolean isValid(String data) {
        boolean isValid;
        SimpleDateFormat format = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
        format.setLenient(false);
        Date dateNow = new Date();

        try {
            Date date = format.parse(data);
            isValid = checkTime(date, dateNow);

        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
        }

        return isValid;
    }

    public boolean checkTime(Date date, Date dateNow) {
        if (mRuleAnnotation.past()) {
            return date.getTime() < dateNow.getTime();
        } else if (mRuleAnnotation.future()) {
            return date.getTime() > dateNow.getTime();
        }

        return false;
    }
}
