package com.flightapp.validation;

import com.flightapp.entity.Flight;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ArrivalTimeValidatorTest {

    ArrivalTimeValidator validator = new ArrivalTimeValidator();

    private ConstraintValidatorContext mockContext() {
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder =
                Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);
        Mockito.doNothing().when(context).disableDefaultConstraintViolation();
        Mockito.when(context.getDefaultConstraintMessageTemplate())
                .thenReturn("arrivalTime must be AFTER departureTime");
        Mockito.when(context.buildConstraintViolationWithTemplate(
                Mockito.eq("arrivalTime must be AFTER departureTime")
        )).thenReturn(builder);
        Mockito.when(builder.addPropertyNode(Mockito.anyString()))
                .thenReturn(nodeBuilder);
        Mockito.when(nodeBuilder.addConstraintViolation())
                .thenReturn(context);
        return context;
    }
    @Test
    void valid_times_pass() {
        Flight f = new Flight();
        f.setDepartureTime(LocalDateTime.now());
        f.setArrivalTime(LocalDateTime.now().plusHours(1));
        assertTrue(validator.isValid(f, mockContext()));
    }
    @Test
    void invalid_times_fail() {
        Flight f = new Flight();
        f.setDepartureTime(LocalDateTime.now());
        f.setArrivalTime(LocalDateTime.now().minusHours(1));
        assertFalse(validator.isValid(f, mockContext()));
    }
    @Test
    void null_flight_pass() {
        assertTrue(validator.isValid(null, mockContext()));
    }
    @Test
    void null_times_pass() {
        Flight f = new Flight();
        f.setDepartureTime(null);
        f.setArrivalTime(null);
        assertTrue(validator.isValid(f, mockContext()));
    }
}
