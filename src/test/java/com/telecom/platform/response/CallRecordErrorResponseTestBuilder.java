package com.telecom.platform.response;

import com.telecom.platform.validators.ValidationError;

import java.util.Collections;
import java.util.List;

public class CallRecordErrorResponseTestBuilder {

    private int code = 500;
    private String message = "Error when saving call record";
    private List<ValidationError> errors = Collections.singletonList(
            new ValidationError("timestamp", "invalid timestamp")
    );

    public CallRecordErrorResponse build() {
        return new CallRecordErrorResponse(
                code,
                message,
                errors
        );
    }


    public CallRecordErrorResponseTestBuilder withErrors(List<ValidationError> validationErrors) {
        this.errors = validationErrors;
        return this;
    }
}
