package com.telecom.platform.request;

public class CallRecordRequestResourceTestBuilder {

    private String type = "start";
    private String timestamp = "2016-02-29T12:00:00Z";
    private String callId = "2";
    private String source = "2799347645";
    private String destination = "5198762356";

    public CallRecordRequestResourceTestBuilder withSource(String source) {
        this.source = source;
        return this;
    }

    public CallRecordRequestResource build() {
        return new CallRecordRequestResource(
                type,
                timestamp,
                callId,
                source,
                destination);
    }
}
