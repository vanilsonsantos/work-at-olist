package com.telecom.platform.controller;

import com.telecom.platform.exceptions.InvalidRequestResourceException;
import com.telecom.platform.service.TelephoneCallService;
import com.telecom.platform.validators.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.telecom.platform.request.CallRecordRequestResource;

@RestController
@RequestMapping("/telecom")
public class TelephoneCallController {

    private ValidationChecker validationChecker;
    private TelephoneCallService telephoneCallService;

    @Autowired
    public TelephoneCallController(ValidationChecker validationChecker, TelephoneCallService telephoneCallService) {
        this.validationChecker = validationChecker;
        this.telephoneCallService = telephoneCallService;
    }

    @RequestMapping(value = "/calls", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity recordCall(@RequestBody CallRecordRequestResource callRecordRequestResource) throws InvalidRequestResourceException {
        validationChecker.validate(callRecordRequestResource);
        return new ResponseEntity(telephoneCallService.save(callRecordRequestResource), HttpStatus.CREATED);
    }

}
