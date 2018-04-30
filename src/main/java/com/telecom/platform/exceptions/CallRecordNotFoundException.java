package com.telecom.platform.exceptions;

public class CallRecordNotFoundException extends Throwable {

    private static final String MESSAGE = "Call record with id %s not found";

    public CallRecordNotFoundException(String expectedCallRecordId) {
        super(String.format(MESSAGE, expectedCallRecordId));
    }
}
