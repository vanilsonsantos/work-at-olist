package com.telecom.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.platform.Application;
import com.telecom.platform.domain.CallRecord;
import com.telecom.platform.exceptions.GlobalExceptionHandler;
import com.telecom.platform.exceptions.InvalidRequestResourceException;
import com.telecom.platform.request.CallRecordRequestResource;
import com.telecom.platform.service.TelephoneCallService;
import com.telecom.platform.validators.ValidationChecker;
import com.telecom.platform.validators.ValidationError;
import com.telecom.platform.domain.CallRecordTestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.telecom.platform.request.RecordCallRequestResourceTestBuilder;
import com.telecom.platform.response.CallRecordErrorResponseTestBuilder;

import java.util.Collections;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_VALUES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest()
public class TelephoneCallControllerTest {

    @Mock
    private TelephoneCallService telephoneCallService;

    @Mock
    private ValidationChecker validationChecker;

    @InjectMocks
    private TelephoneCallController telephoneCallController;

    private MockMvc mvc;
    private RecordCallRequestResourceTestBuilder recordCallRequestResourceTestBuilder;
    private CallRecordErrorResponseTestBuilder callRecordErrorResponseTestBuilder;
    private CallRecordTestBuilder callRecordTestBuilder;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(telephoneCallController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        recordCallRequestResourceTestBuilder = new RecordCallRequestResourceTestBuilder();
        callRecordErrorResponseTestBuilder = new CallRecordErrorResponseTestBuilder();
        callRecordTestBuilder = new CallRecordTestBuilder();
    }

    @Test
    public void shouldReturn422StatusIfRequestBodyIsInvalidWhenCreatingNewCallRecord() throws Exception {
        //given
        CallRecordRequestResource recordCallRequestResource = recordCallRequestResourceTestBuilder
                .build();

        doThrow(new InvalidRequestResourceException(
                Collections.singletonList(new ValidationError("source", "Invalid source")),
                "Invalid source message"
        )).when(validationChecker).validate(any());

        //when
        ResultActions response = mvc.perform(post("/telecom/calls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recordCallRequestResource))
        );

        //then
        response.andExpect(status().is(422));
        assertJsonEquals(
                asJsonString(callRecordErrorResponseTestBuilder.build()),
                response.andReturn().getResponse().getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
    }

    @Test
    public void shouldReturn201StatusWhenCreatingNewCallRecord() throws Exception {
        //given
        CallRecordRequestResource invalidRecordCallRequestResource = recordCallRequestResourceTestBuilder
                .build();
        CallRecord expectedCallRecordResponse = callRecordTestBuilder.build();
        when(telephoneCallService.save(any())).thenReturn(expectedCallRecordResponse);

        //when
        ResultActions response = mvc.perform(post("/telecom/calls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidRecordCallRequestResource))
        );

        //then
        response.andExpect(status().is(201));
        assertJsonEquals(
                asJsonString(expectedCallRecordResponse),
                response.andReturn().getResponse().getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
    }

    @Test
    public void shouldReturn200StatusWhenGettingCallRecordById() throws Exception {
        //given
        CallRecordRequestResource invalidRecordCallRequestResource = recordCallRequestResourceTestBuilder
                .build();
        CallRecord expectedCallRecordResponse = callRecordTestBuilder.build();
        when(telephoneCallService.findById(anyString())).thenReturn(expectedCallRecordResponse);

        //when
        ResultActions response = mvc.perform(get("/telecom/calls/{id}", "5ae762aa106e481143ff33b6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidRecordCallRequestResource))
        );

        //then
        response.andExpect(status().is(200));
        assertJsonEquals(
                asJsonString(expectedCallRecordResponse),
                response.andReturn().getResponse().getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
