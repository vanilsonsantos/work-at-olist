package com.telecom.platform.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.telecom.platform.repository.documents.CallRecordDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallRecord {

    private String id;
    private String type;
    private String timestamp;
    @JsonProperty("call_id")
    private String callId;
    private String source;
    private String destination;

    public CallRecord(CallRecordDocument callRecordDocument) {
        this.id = callRecordDocument.getId();
        this.type = callRecordDocument.getType();
        this.timestamp = callRecordDocument.getTimestamp();
        this.callId = callRecordDocument.getCall_id();
        this.source = callRecordDocument.getSource();
        this.destination = callRecordDocument.getDestination();
    }
}
