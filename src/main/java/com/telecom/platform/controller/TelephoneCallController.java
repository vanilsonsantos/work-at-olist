package com.telecom.platform.controller;

import com.telecom.platform.domain.CallRecord;
import com.telecom.platform.exceptions.CallRecordNotFoundException;
import com.telecom.platform.exceptions.InvalidRequestResourceException;
import com.telecom.platform.request.CallRecordRequestResource;
import com.telecom.platform.service.TelephoneCallService;
import com.telecom.platform.validators.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static com.telecom.platform.utils.TelephoneCallUrls.CALLS_BY_ID_ENDPOINT;
import static com.telecom.platform.utils.TelephoneCallUrls.CALLS_ENDPOINT;

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

    @RequestMapping(value = CALLS_ENDPOINT, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveRecordCall(UriComponentsBuilder uriComponentsBuilder,
                                         @RequestBody CallRecordRequestResource callRecordRequestResource) throws InvalidRequestResourceException {
        validationChecker.validate(callRecordRequestResource);
        CallRecord callRecord = telephoneCallService.save(callRecordRequestResource);
        UriComponents uriComponents = uriComponentsBuilder.path(CALLS_BY_ID_ENDPOINT).buildAndExpand(callRecord.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(callRecord);
    }

    @RequestMapping(value = CALLS_BY_ID_ENDPOINT, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getRecordCall(@PathVariable String id) throws CallRecordNotFoundException {
        return ResponseEntity.ok().body(telephoneCallService.findById(id));
    }

}
