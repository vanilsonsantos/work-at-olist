package com.telecom.platform.service;

import com.telecom.platform.domain.CallRecord;
import com.telecom.platform.request.CallRecordRequestResource;
import org.springframework.stereotype.Service;

@Service
public class TelephoneCallService {

    public CallRecord save(CallRecordRequestResource callRecordRequestResource) {
        return new CallRecord(
                "8686abdd-f024-4e96-a546-71349f2e2ecb",
                callRecordRequestResource.getType(),
                callRecordRequestResource.getTimestamp(),
                callRecordRequestResource.getCallId(),
                callRecordRequestResource.getSource(),
                callRecordRequestResource.getDestination()
        );
    }
}
