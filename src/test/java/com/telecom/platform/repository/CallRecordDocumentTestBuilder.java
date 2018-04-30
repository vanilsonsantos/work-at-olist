package com.telecom.platform.repository;

import com.telecom.platform.repository.documents.CallRecordDocument;

public class CallRecordDocumentTestBuilder {

    private String id = "29f0b18f-939b-4f07-aa5d-a93b22eca16e";
    private String type = "start";
    private String timestamp = "2016-02-29T12:00:00Z";
    private String callId = "2";
    private String source = "2799347645";
    private String destination = "5198762356";

    public CallRecordDocument build() {
        return new CallRecordDocument(
                id,
                type,
                timestamp,
                callId,
                source,
                destination);
    }

}
