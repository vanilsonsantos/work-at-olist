package request;

import com.telecom.platform.exceptions.InvalidRequestResourceException;
import com.telecom.platform.request.CallRecordRequestResource;
import com.telecom.platform.validators.ValidationChecker;
import com.telecom.platform.validators.ValidationError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.Validation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.telecom.platform.validators.ValidationMessages.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CallRecordRequestResourceParameterizedTest {

    private ValidationChecker validationChecker;

    private String type;
    private String timestamp;
    private String callId;
    private String source;
    private String destination;
    private String message;

    public CallRecordRequestResourceParameterizedTest(String type,
                                                      String timestamp,
                                                      String callId,
                                                      String source,
                                                      String destination,
                                                      String message) {
        this.type = type;
        this.timestamp = timestamp;
        this.callId = callId;
        this.source = source;
        this.destination = destination;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "", "2016-02-29T12:00:00Z", "1", "2799347645", "5198762356", INVALID_TYPE_MESSAGE },
                { null, "2016-02-29T12:00:00Z", "1", "2799347645", "5198762356", NULL_TYPE_MESSAGE },
                { "start", "2016-XX-29T12:XX:00Z", "2", "2799347645", "5198762356", INVALID_TIMESTAMP_MESSAGE },
                { "start", null, "2", "2799347645", "5198762356", NULL_TIMESTAMP_MESSAGE },
                { "start", "2016-02-29T12:00:00Z", "5s", "2799347645", "5198762356", INVALID_CALL_ID_MESSAGE },
                { "start", "2016-02-29T12:00:00Z", null, "2799347645", "5198762356", NULL_CALL_ID_MESSAGE },
                { "start", "2016-02-29T12:00:00Z", "3", "8198345", "5198762356", INVALID_SOURCE_NUMBER_MESSAGE },
                { "start", "2016-02-29T12:00:00Z", "4", "2799347645", "23456", INVALID_DESTINATION_NUMBER_MESSAGE },
                { "start", "2016-02-29T12:00:00Z", "6", "2799347645", "5198762356", "" },
                { "end", "2016-02-29T12:00:00Z", "7", "XXXXX", "XXXXX", "" }
        });
    }

    @Before
    public void setUp() {
        validationChecker = new ValidationChecker(Validation.buildDefaultValidatorFactory().getValidator());
    }

    @Test
    public void shouldCatchExceptionIfConstraintWasViolated() {
        try {
            validationChecker.validate(new CallRecordRequestResource(type, timestamp, callId, source, destination));
            assertThat(message, isEmptyString());
        } catch (InvalidRequestResourceException ex) {
            assertTrue(getErrorMessage(ex.getErrors()).contains(message));
        }
    }

    private String getErrorMessage(List<ValidationError> errors) {
        return errors.stream()
                .map(ValidationError::getMessage)
                .collect(Collectors.toList())
                .toString();
    }
}
