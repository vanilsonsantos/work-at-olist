package com.telecom.platform.service;

import com.telecom.platform.domain.CallRecord;
import com.telecom.platform.repository.CallRecordRepository;
import com.telecom.platform.repository.documents.CallRecordDocument;
import com.telecom.platform.request.CallRecordRequestResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelephoneCallService {

    private CallRecordRepository callRecordRepository;

    @Autowired
    public TelephoneCallService(CallRecordRepository callRecordRepository) {
        this.callRecordRepository = callRecordRepository;
    }

    public CallRecord save(CallRecordRequestResource callRecordRequestResource) {
        CallRecordDocument callRecordDocument = callRecordRepository.save(new CallRecordDocument(
                callRecordRequestResource.getType(),
                callRecordRequestResource.getTimestamp(),
                callRecordRequestResource.getCallId(),
                callRecordRequestResource.getSource(),
                callRecordRequestResource.getDestination()));
        return new CallRecord(callRecordDocument);
    }
}
