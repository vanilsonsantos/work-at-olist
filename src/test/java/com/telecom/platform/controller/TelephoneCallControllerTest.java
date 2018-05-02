package com.telecom.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.platform.Application;
import com.telecom.platform.domain.CallRecord;
import com.telecom.platform.domain.CallRecordTestBuilder;
import com.telecom.platform.exceptions.CallRecordNotFoundException;
import com.telecom.platform.exceptions.GlobalExceptionHandler;
import com.telecom.platform.exceptions.InvalidRequestResourceException;
import com.telecom.platform.request.CallRecordRequestResource;
import com.telecom.platform.request.CallRecordRequestResourceTestBuilder;
import com.telecom.platform.response.CallRecordErrorResponse;
import com.telecom.platform.response.CallRecordErrorResponseTestBuilder;
import com.telecom.platform.service.TelephoneCallService;
import com.telecom.platform.validators.ValidationChecker;
import com.telecom.platform.validators.ValidationError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_VALUES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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
    private CallRecordRequestResourceTestBuilder callRecordRequestResourceTestBuilder;
    private CallRecordErrorResponseTestBuilder callRecordErrorResponseTestBuilder;
    private CallRecordTestBuilder callRecordTestBuilder;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(telephoneCallController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        callRecordRequestResourceTestBuilder = new CallRecordRequestResourceTestBuilder();
        callRecordErrorResponseTestBuilder = new CallRecordErrorResponseTestBuilder();
        callRecordTestBuilder = new CallRecordTestBuilder();
    }

    @Test
    public void shouldReturn422StatusIfRequestBodyIsInvalidWhenCreatingNewCallRecord() throws Exception {
        //given
        CallRecordRequestResource recordCallRequestResource = callRecordRequestResourceTestBuilder
                .build();
        doThrow(new InvalidRequestResourceException(
                Collections.singletonList(new ValidationError("source", "Invalid source")),
                "Invalid source message"
        )).when(validationChecker).validate(any());

        //when
        ResultActions response = mvc.perform(post("/calls")
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
        CallRecordRequestResource invalidRecordCallRequestResource = callRecordRequestResourceTestBuilder
                .build();
        CallRecord expectedCallRecordResponse = callRecordTestBuilder.build();
        when(telephoneCallService.save(any())).thenReturn(expectedCallRecordResponse);

        //when
        ResultActions response = mvc.perform(post("/calls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidRecordCallRequestResource))
        );

        //then
        response.andExpect(status().is(201));
        MockHttpServletResponse result = response.andReturn().getResponse();
        String expectedLocationUrl = String.format("http://localhost/calls/%s", expectedCallRecordResponse.getId());
        assertJsonEquals(
                asJsonString(expectedCallRecordResponse),
                result.getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
        assertThat(result.getHeader("location"), is(expectedLocationUrl));
    }

    @Test
    public void shouldReturn200StatusWhenGettingCallRecordById() throws Exception, CallRecordNotFoundException {
        //given
        CallRecord expectedCallRecordResponse = callRecordTestBuilder.build();
        when(telephoneCallService.findById(anyString())).thenReturn(expectedCallRecordResponse);

        //when
        ResultActions response = mvc.perform(get("/calls/{id}", "5ae762aa106e481143ff33b6")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andExpect(status().is(200));
        assertJsonEquals(
                asJsonString(expectedCallRecordResponse),
                response.andReturn().getResponse().getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
    }

    @Test
    public void shouldReturn404StatusWhenGettingCallRecordByIdAndRecordDoesNotExists() throws Exception, CallRecordNotFoundException {
        //given
        CallRecordErrorResponse expectedCallRecordResponse = callRecordErrorResponseTestBuilder
                .withErrors(null)
                .build();
        String expectedCallRecordId = "5ae762aa106e481143ff33b6";
        when(telephoneCallService.findById(anyString())).thenThrow(new CallRecordNotFoundException(expectedCallRecordId));

        //when
        ResultActions response = mvc.perform(get("/calls/{id}", expectedCallRecordId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        response.andExpect(status().is(404));
        assertJsonEquals(
                asJsonString(expectedCallRecordResponse),
                response.andReturn().getResponse().getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
