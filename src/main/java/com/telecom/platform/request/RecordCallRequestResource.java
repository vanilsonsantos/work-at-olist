package com.telecom.platform.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.telecom.platform.validators.RequestResource;
import com.telecom.platform.validators.ValidTimestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.telecom.platform.validators.ValidationMessages.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecordCallRequestResource implements RequestResource {

    @NotNull(message = NULL_TYPE_MESSAGE)
    @Pattern(regexp = "start|end", message = INVALID_TYPE_MESSAGE)
    @JsonProperty("type")
    private String type;

    @NotNull(message = NULL_TIMESTAMP_MESSAGE)
    @ValidTimestamp
    @JsonProperty("timestamp")
    private String timestamp;

    @NotNull(message = NULL_CALL_ID_MESSAGE)
    @Pattern(regexp = "^[+]?\\d+$", message = INVALID_CALL_ID_MESSAGE)
    @JsonProperty("call_id")
    private String callId;

    @JsonProperty("source")
    private String source;

    @JsonProperty("destination")
    private String destination;

    @AssertTrue(message = INVALID_SOURCE_NUMBER_MESSAGE)
    public boolean isValidSourceNumber() {
        if (shouldIncludePhoneNumber()) {
            return isValidPhoneNumber(source);
        }
        return true;
    }

    @AssertTrue(message = INVALID_DESTINATION_NUMBER_MESSAGE)
    public boolean isValidDestinationNumber() {
        if (shouldIncludePhoneNumber()) {
            return isValidPhoneNumber(destination);
        }
        return true;
    }

    private boolean shouldIncludePhoneNumber() {
        return type != null && type.equalsIgnoreCase("start");
    }

    private boolean isValidPhoneNumber(String number) {
        if (number == null || number.length() > 11 || number.length() < 10) {
            return false;
        } else {
            String validPhoneNumberRegularExpression = "ˆ?(?:([1-9][0-9])\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(validPhoneNumberRegularExpression);
            return pattern.matcher(number).matches();
        }
    }
}
