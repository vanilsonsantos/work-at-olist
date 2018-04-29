package com.telecom.platform.validators;

public final class ValidationMessages {

    public static final String INVALID_TYPE_MESSAGE = "Type should be start or end";
    public static final String NULL_TYPE_MESSAGE = "Type must not be null";
    public static final String INVALID_TIMESTAMP_MESSAGE = "Timestamp should be on the format yyyy-MM-ddTHH:mm:ssZ";
    public static final String NULL_TIMESTAMP_MESSAGE = "Timestamp must not be null";
    public static final String INVALID_CALL_ID_MESSAGE = "Call id should be a positive and valid integer";
    public static final String NULL_CALL_ID_MESSAGE = "Call id must not be null";
    public static final String INVALID_SOURCE_NUMBER_MESSAGE = "Source phone number should be on the format AAXXXXXXXXX, where AA is the area code and " +
            "XXXXXXXXX is the phone number. The phone number is composed of 8 or 9 digits";
    public static final String INVALID_DESTINATION_NUMBER_MESSAGE = "Destination phone number should be on the format AAXXXXXXXXX, where AA is the area code and " +
            "XXXXXXXXX is the phone number. The phone number is composed of 8 or 9 digits";

    private ValidationMessages() {
        throw new RuntimeException();
    }
}
