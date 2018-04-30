package com.telecom.platform.response;

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
public class CallRecordErrorResponse {
    private int code;
    private String message;
    private List<ValidationError> errors;
}
