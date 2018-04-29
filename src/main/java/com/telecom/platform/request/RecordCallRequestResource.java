package com.telecom.platform.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.telecom.platform.validators.RequestResource;
import com.telecom.platform.validators.ValidPhoneNumber;
import com.telecom.platform.validators.ValidTimestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.telecom.platform.validators.ValidationMessages.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ValidPhoneNumber
@JsonIgnoreProperties(ignoreUnknown = true)
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
}
