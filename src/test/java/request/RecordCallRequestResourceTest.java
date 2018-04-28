package request;

import com.telecom.platform.request.RecordCallRequestResource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecordCallRequestResourceTest {

    private RecordCallRequestResourceTestBuilder recordCallRequestResourceTestBuilder;

    @Before
    public void setUp() {
        recordCallRequestResourceTestBuilder = new RecordCallRequestResourceTestBuilder();
    }

    @Test
    public void shouldSourceBeValid() {
        RecordCallRequestResource recordCallWithEightDigitsSourceNumber = recordCallRequestResourceTestBuilder
                .withSource("2799347645")
                .build();
        RecordCallRequestResource recordCallWithNineDigitsSourceNumber = recordCallRequestResourceTestBuilder
                .withSource("27993476457")
                .build();

        assertTrue(recordCallWithEightDigitsSourceNumber.isValidSourceNumber());
        assertTrue(recordCallWithNineDigitsSourceNumber.isValidSourceNumber());
    }

    @Test
    public void shouldSourceBeInvalid() {
        assertFalse(recordCallRequestResourceTestBuilder.withSource("7623456").build().isValidSourceNumber());
    }

    @Test
    public void shouldDestinationBeValid() {
        RecordCallRequestResource recordCallWithEightDigitsDestinationNumber = recordCallRequestResourceTestBuilder
                .withDestination("2799347645")
                .build();
        RecordCallRequestResource recordCallWithNineDigitsDestinationNumber = recordCallRequestResourceTestBuilder
                .withDestination("27993476457")
                .build();

        assertTrue(recordCallWithEightDigitsDestinationNumber.isValidSourceNumber());
        assertTrue(recordCallWithNineDigitsDestinationNumber.isValidSourceNumber());
    }

    @Test
    public void shouldDestinationBeInvalid() {
        assertFalse(recordCallRequestResourceTestBuilder.withDestination("7623456").build().isValidDestinationNumber());
    }

}
