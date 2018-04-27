package com.telecom.platform.validators;

import com.telecom.platform.exceptions.InvalidRequestResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationChecker {

    private Validator validator;

    @Autowired
    public ValidationChecker(Validator validator) {
        this.validator = validator;
    }

    public void validate(RequestResource requestParameterResource) throws InvalidRequestResourceException {

        Set<ConstraintViolation<RequestResource>> constraintViolations = validator.validate(requestParameterResource);

        if(!constraintViolations.isEmpty()) {

            String message = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList())
                    .toString();

            throw new InvalidRequestResourceException(message);
        }
    }
}
