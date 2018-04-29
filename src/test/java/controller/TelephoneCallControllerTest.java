package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.platform.Application;
import com.telecom.platform.request.RecordCallRequestResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import request.RecordCallRequestResourceTestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest()

public class TelephoneCallControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private RecordCallRequestResourceTestBuilder recordCallRequestResourceTestBuilder;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        recordCallRequestResourceTestBuilder = new RecordCallRequestResourceTestBuilder();
    }

    @Test
    public void should422IfRequestBodyIsInvalidWhenCreatingNewCallRecord() throws Exception {
        RecordCallRequestResource invalidRecordCallRequestResource = recordCallRequestResourceTestBuilder
                .withSource("11-invalid-99")
                .build();

        mvc.perform(post("/telecom/calls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidRecordCallRequestResource))
        )
                .andExpect(status().is(422));
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
