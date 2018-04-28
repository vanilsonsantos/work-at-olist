package validators;

import com.telecom.platform.validators.TimestampValidator;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class TimestampValidatorTest {

    private TimestampValidator timestampValidator = new TimestampValidator();

    @Test
    public void shouldTimestampBeValid() {
        assertTrue(timestampValidator.isValid("2016-02-29T12:00:00Z", null));
    }

    @Test
    public void shouldTimestampBeInvalid() {
        assertFalse(timestampValidator.isValid("2016-02-XXXXX:00:00Z", null));
    }
}
