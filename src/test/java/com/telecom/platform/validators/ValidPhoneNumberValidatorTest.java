package com.telecom.platform.validators;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import com.telecom.platform.request.RecordCallRequestResourceTestBuilder;

import javax.validation.ConstraintValidatorContext;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ValidPhoneNumberValidatorTest {

    private ValidPhoneNumberValidator validPhoneNumberValidator = new ValidPhoneNumberValidator();
    private RecordCallRequestResourceTestBuilder recordCallRequestResourceTestBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Before
    public void setUp() {
        initMocks(this);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(constraintValidatorContext);
        when(constraintViolationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilderCustomizableContext);
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        recordCallRequestResourceTestBuilder = new RecordCallRequestResourceTestBuilder();
    }

    @Test
    public void shouldPhoneNumberBeValid() {
        boolean isValid = validPhoneNumberValidator.isValid(
                recordCallRequestResourceTestBuilder.build(),
                constraintValidatorContext
        );
        assertTrue(isValid);
    }

    @Test
    public void shouldPhoneNumberBeInvalid() {
        boolean isValid = validPhoneNumberValidator.isValid(
                recordCallRequestResourceTestBuilder.withSource("2016-02-invalid:00:00Z").build(),
                constraintValidatorContext
        );
        assertFalse(isValid);
    }
}
