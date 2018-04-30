package com.telecom.platform.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.telecom.platform.validators.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallRecordErrorResponse {
    private int code;
    private String message;
    private List<ValidationError> errors;
}
