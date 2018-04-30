package com.telecom.platform.validators;

import com.telecom.platform.request.CallRecordRequestResource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static com.telecom.platform.validators.ValidationMessages.INVALID_DESTINATION_NUMBER_MESSAGE;
import static com.telecom.platform.validators.ValidationMessages.INVALID_SOURCE_NUMBER_MESSAGE;
import static java.util.regex.Pattern.compile;

public class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, CallRecordRequestResource> {
    @Override
    public boolean isValid(CallRecordRequestResource callRecordRequestResource, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (shouldValidatePhoneNumber(callRecordRequestResource.getType())) {
            boolean isValidSourceNumber = isValidPhoneNumber(callRecordRequestResource.getSource());
            boolean isValidDestinationNumber = isValidPhoneNumber(callRecordRequestResource.getDestination());
            if (!isValidSourceNumber) {
                context.buildConstraintViolationWithTemplate(INVALID_SOURCE_NUMBER_MESSAGE)
                        .addPropertyNode("source")
                        .addConstraintViolation();
            }
            if (!isValidDestinationNumber) {
                context.buildConstraintViolationWithTemplate(INVALID_DESTINATION_NUMBER_MESSAGE)
                        .addPropertyNode("destination")
                        .addConstraintViolation();
            }
            return isValidSourceNumber && isValidDestinationNumber;
        }
        return true;
    }

    private boolean shouldValidatePhoneNumber(String type) {
        return type != null && type.equalsIgnoreCase("start");
    }

    private boolean isValidPhoneNumber(String number) {
        if (number == null || number.length() > 11 || number.length() < 10) {
            return false;
        } else {
            String validPhoneNumberRegularExpression = "ˆ?(?:([1-9][0-9])\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$";
            Pattern pattern = compile(validPhoneNumberRegularExpression);
            return pattern.matcher(number).matches();
        }
    }
}
