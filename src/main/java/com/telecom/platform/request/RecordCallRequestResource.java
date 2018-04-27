package com.telecom.platform.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.telecom.platform.validators.RequestResource;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.telecom.platform.validators.ValidationMessages.*;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class RecordCallRequestResource implements RequestResource {

    @Pattern(regexp = "start|end", message = INVALID_TYPE_MESSAGE)
    @JsonProperty("type")
    private String type;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("call_id")
    @Pattern(regexp = "^[+]?\\d+$", message = INVALID_CALL_ID_MESSAGE)
    private String callId;

    @JsonProperty("source")
    private String source;

    @JsonProperty("destination")
    private String destination;

    public RecordCallRequestResource(String type,
                                     String timestamp,
                                     String callId,
                                     String source,
                                     String destination) {
        this.type = type;
        this.timestamp = timestamp;
        this.callId = callId;
        this.source = source;
        this.destination = destination;
    }

    @AssertTrue(message = INVALID_TIMESTAMP_MESSAGE)
    public boolean isValidTimestamp() {
        String validTimestampFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        DateFormat dateFormat = new SimpleDateFormat(validTimestampFormat);
        try {
            dateFormat.parse(timestamp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @AssertTrue(message = INVALID_SOURCE_NUMBER_MESSAGE)
    public boolean isValidSourceNumber() {
        if (source != null && shouldIncludePhoneNumber()) {
            return isValidPhoneNumber(source);
        }
        return true;
    }

    @AssertTrue(message = INVALID_DESTINATION_NUMBER_MESSAGE)
    public boolean isValidDestinationNumber() {
        if (destination != null && shouldIncludePhoneNumber()) {
            return isValidPhoneNumber(destination);
        }
        return true;
    }

    private boolean shouldIncludePhoneNumber() {
        return type != null && type.equalsIgnoreCase("start");
    }

    private boolean isValidPhoneNumber(String number) {
        if (number.length() > 11 || number.length() < 10) {
            return false;
        } else {
            String validPhoneNumberRegularExpression = "ˆ?(?:([1-9][0-9])\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(validPhoneNumberRegularExpression);
            return pattern.matcher(number).matches();
        }
    }
}
