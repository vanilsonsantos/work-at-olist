package com.telecom.platform.repository;

import com.telecom.platform.repository.documents.CallRecordDocument;
import com.telecom.platform.request.CallRecordRequestResource;
import com.telecom.platform.request.RecordCallRequestResourceTestBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = { ConfigServerWithFongoConfiguration.class },
        properties = {"server.port=8980" },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(properties = { "spring.data.mongodb.database=test" })
public class CallRecordRepositoryTest {

    @Autowired
    private CallRecordRepository callRecordRepository;

    private RecordCallRequestResourceTestBuilder recordCallRequestResourceTestBuilder;

    @Before
    public void setUp() {
        recordCallRequestResourceTestBuilder = new RecordCallRequestResourceTestBuilder();
    }

    @After
    public void tearDown() {
        callRecordRepository.deleteAll();
    }

    @Test
    public void shouldCreateNewCallRecordDocument() {
        CallRecordRequestResource recordRequestResource = recordCallRequestResourceTestBuilder.build();
        CallRecordDocument callRecordDocument = callRecordRepository.save(new CallRecordDocument(
                recordRequestResource.getType(),
                recordRequestResource.getTimestamp(),
                recordRequestResource.getCallId(),
                recordRequestResource.getSource(),
                recordRequestResource.getDestination()));

        assertNotNull(callRecordDocument.getId());
        assertThat(callRecordDocument.getType(), is(recordRequestResource.getType()));
        assertThat(callRecordDocument.getTimestamp(), is(recordRequestResource.getTimestamp()));
        assertThat(callRecordDocument.getCall_id(), is(recordRequestResource.getCallId()));
        assertThat(callRecordDocument.getSource(), is(recordRequestResource.getSource()));
        assertThat(callRecordDocument.getDestination(), is(recordRequestResource.getDestination()));
    }

    @Test
    public void shouldGetNewCallRecordDocumentById() {
        CallRecordRequestResource recordRequestResource = recordCallRequestResourceTestBuilder.build();
        CallRecordDocument callRecordDocument = callRecordRepository.save(new CallRecordDocument(
                recordRequestResource.getType(),
                recordRequestResource.getTimestamp(),
                recordRequestResource.getCallId(),
                recordRequestResource.getSource(),
                recordRequestResource.getDestination()));

        Optional<CallRecordDocument> returnedCallRecordDocument =
                callRecordRepository.findById(callRecordDocument.getId());

        assertThat(returnedCallRecordDocument.isPresent(), is(true));

        CallRecordDocument recordDocument = returnedCallRecordDocument.get();
        assertNotNull(recordDocument.getId());
        assertThat(recordDocument.getType(), is(recordRequestResource.getType()));
        assertThat(recordDocument.getTimestamp(), is(recordRequestResource.getTimestamp()));
        assertThat(recordDocument.getCall_id(), is(recordRequestResource.getCallId()));
        assertThat(recordDocument.getSource(), is(recordRequestResource.getSource()));
        assertThat(recordDocument.getDestination(), is(recordRequestResource.getDestination()));
    }
}
