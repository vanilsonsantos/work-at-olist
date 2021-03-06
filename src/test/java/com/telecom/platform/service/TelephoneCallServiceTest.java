package com.telecom.platform.service;

import com.telecom.platform.domain.CallRecord;
import com.telecom.platform.exceptions.CallRecordNotFoundException;
import com.telecom.platform.repository.CallRecordDocumentTestBuilder;
import com.telecom.platform.repository.CallRecordRepository;
import com.telecom.platform.repository.documents.CallRecordDocument;
import com.telecom.platform.request.CallRecordRequestResourceTestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TelephoneCallServiceTest {

    @Mock
    private CallRecordRepository callRecordRepository;

    private TelephoneCallService telephoneCallService;
    private CallRecordDocumentTestBuilder callRecordDocumentTestBuilder;

    @Before
    public void setUp() {
        initMocks(this);
        telephoneCallService = new TelephoneCallService(callRecordRepository);
        callRecordDocumentTestBuilder = new CallRecordDocumentTestBuilder();
    }

    @Test
    public void shouldCreateAndReturnNewCallRecord() {
        CallRecordDocument callRecordDocument = callRecordDocumentTestBuilder.build();
        when(callRecordRepository.save(any())).thenReturn(callRecordDocument);

        CallRecord callRecord = telephoneCallService.save(new CallRecordRequestResourceTestBuilder().build());
        assertThat(callRecord.getId(), is(callRecordDocument.getId()));
        assertThat(callRecord.getType(), is(callRecordDocument.getType()));
        assertThat(callRecord.getTimestamp(), is(callRecordDocument.getTimestamp()));
        assertThat(callRecord.getCallId(), is(callRecordDocument.getCall_id()));
        assertThat(callRecord.getSource(), is(callRecordDocument.getSource()));
        assertThat(callRecord.getDestination(), is(callRecordDocument.getDestination()));
    }

    @Test
    public void shouldReturnCallRecordWhenGettingById() throws CallRecordNotFoundException {
        CallRecordDocument callRecordDocument = callRecordDocumentTestBuilder.build();
        when(callRecordRepository.findById(any())).thenReturn(Optional.of(callRecordDocument));

        CallRecord callRecord = telephoneCallService.findById(callRecordDocument.getId());
        assertThat(callRecord.getId(), is(callRecordDocument.getId()));
        assertThat(callRecord.getType(), is(callRecordDocument.getType()));
        assertThat(callRecord.getTimestamp(), is(callRecordDocument.getTimestamp()));
        assertThat(callRecord.getCallId(), is(callRecordDocument.getCall_id()));
        assertThat(callRecord.getSource(), is(callRecordDocument.getSource()));
        assertThat(callRecord.getDestination(), is(callRecordDocument.getDestination()));
    }
}