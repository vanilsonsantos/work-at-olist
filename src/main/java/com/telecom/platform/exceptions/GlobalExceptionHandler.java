package com.telecom.platform.exceptions;

import com.telecom.platform.response.CallRecordErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestResourceException.class)
    public ResponseEntity handleInvalidRequestResourceException(InvalidRequestResourceException ex) {
        return ResponseEntity.unprocessableEntity().body(new CallRecordErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getMessage(),
                ex.getErrors()
            )
        );
    }

    @ExceptionHandler(CallRecordNotFoundException.class)
    public ResponseEntity handleCallRecordNotFoundException(CallRecordNotFoundException ex) {
        return new ResponseEntity(new CallRecordErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        ), HttpStatus.NOT_FOUND);
    }

}
