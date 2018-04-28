package com.telecom.platform.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimestampValidator implements ConstraintValidator<ValidTimestamp, String> {
    @Override
    public boolean isValid(String timestamp, ConstraintValidatorContext context) {
        String validTimestampFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        DateFormat dateFormat = new SimpleDateFormat(validTimestampFormat);
        try {
            dateFormat.parse(timestamp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
