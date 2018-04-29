package request;

import com.telecom.platform.request.RecordCallRequestResource;

public class RecordCallRequestResourceTestBuilder {

    private String type = "start";
    private String timestamp = "2016-02-29T12:00:00Z";
    private String callId = "2";
    private String source = "2799347645";
    private String destination = "5198762356";

    public RecordCallRequestResourceTestBuilder withSource(String source) {
        this.source = source;
        return this;
    }

    public RecordCallRequestResource build() {
        return new RecordCallRequestResource(
                type,
                timestamp,
                callId,
                source,
                destination);
    }
}
