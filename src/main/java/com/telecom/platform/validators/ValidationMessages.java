package com.telecom.platform.validators;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

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

    public static final Map<String, String> VALIDATION_ERRORS_MAPPER = ImmutableMap.<String, String>builder()
            .put(INVALID_TYPE_MESSAGE, "type")
            .put(NULL_TYPE_MESSAGE, "type")
            .put(INVALID_TIMESTAMP_MESSAGE, "timestamp")
            .put(NULL_TIMESTAMP_MESSAGE, "timestamp")
            .put(INVALID_CALL_ID_MESSAGE, "call_id")
            .put(NULL_CALL_ID_MESSAGE, "call_id")
            .put(INVALID_SOURCE_NUMBER_MESSAGE, "source")
            .put(INVALID_DESTINATION_NUMBER_MESSAGE, "destination")
            .build();

    private ValidationMessages() {
        throw new RuntimeException();
    }
}
