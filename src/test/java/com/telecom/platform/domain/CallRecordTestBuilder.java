package com.telecom.platform.domain;

public class CallRecordTestBuilder {

    private String id = "29f0b18f-939b-4f07-aa5d-a93b22eca16e";
    private String type = "start";
    private String timestamp = "2016-02-29T12:00:00Z";
    private String callId = "2";
    private String source = "2799347645";
    private String destination = "5198762356";

    public CallRecord build() {
        return new CallRecord(
                id,
                type,
                timestamp,
                callId,
                source,
                destination
        );
    }
}
