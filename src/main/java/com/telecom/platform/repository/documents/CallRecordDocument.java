package com.telecom.platform.repository.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class CallRecordDocument {

    @Id
    private String id;
    private String type;
    private String timestamp;
    private String call_id;
    private String source;
    private String destination;

    public CallRecordDocument(String type,
                              String timestamp,
                              String call_id,
                              String source,
                              String destination) {
        this.type = type;
        this.timestamp = timestamp;
        this.call_id = call_id;
        this.source = source;
        this.destination = destination;
    }
}
