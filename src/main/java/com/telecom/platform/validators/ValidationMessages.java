package com.telecom.platform.validators;

public interface ValidationMessages {

    String INVALID_TYPE_MESSAGE = "type should be start or end";
    String INVALID_TIMESTAMP_MESSAGE = "timestamp should be on the format yyyy-MM-ddTHH:mm:ssZ";
    String INVALID_CALL_ID_MESSAGE = "call_id should be a positive and valid integer";
    String INVALID_SOURCE_NUMBER_MESSAGE = "number should be on the format AAXXXXXXXXX, where AA is the area code and " +
            "XXXXXXXXX is the phone number. The phone number is composed of 8 or 9 digits";

    String INVALID_DESTINATION_NUMBER_MESSAGE = "number should be on the format AAXXXXXXXXX, where AA is the area code and " +
            "XXXXXXXXX is the phone number. The phone number is composed of 8 or 9 digits";
}
