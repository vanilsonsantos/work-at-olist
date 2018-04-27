package com.telecom.platform.controller;

import com.telecom.platform.exceptions.InvalidRequestResourceException;
import com.telecom.platform.validators.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.telecom.platform.request.RecordCallRequestResource;

@RestController
@RequestMapping("/telecom")
public class TelephoneCallController {

    private ValidationChecker validationChecker;

    @Autowired
    public TelephoneCallController(ValidationChecker validationChecker) {
        this.validationChecker = validationChecker;
    }

    @RequestMapping(value = "/calls", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> recordCall(@RequestBody RecordCallRequestResource recordCallRequestResource) throws InvalidRequestResourceException {
        validationChecker.validate(recordCallRequestResource);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
