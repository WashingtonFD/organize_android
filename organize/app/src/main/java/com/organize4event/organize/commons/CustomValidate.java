package com.organize4event.organize.commons;

import android.widget.EditText;

import com.organize4event.organize.models.Plan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomValidate {

    public boolean validateCodePlan(EditText editText, Plan plan){
        String code = editText.getText().toString();
        return code.equals(plan.getSecurity_code());
    }

    public boolean validadeEmail(String email){
        if ((email == null) || (email.trim().length() == 0))
            return false;
        String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
