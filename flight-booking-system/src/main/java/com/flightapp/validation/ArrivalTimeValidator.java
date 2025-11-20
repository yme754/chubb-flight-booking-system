package com.flightapp.validation;

import com.flightapp.entity.Flight;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArrivalTimeValidator implements ConstraintValidator<ValidArrivalTime, Flight> {
	@Override
    public boolean isValid(Flight flight, ConstraintValidatorContext context) {
        if (flight == null) return true;
        if (flight.getDepartureTime() == null || flight.getArrivalTime() == null) return true;
        boolean valid = flight.getArrivalTime().isAfter(flight.getDepartureTime());
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                   .addPropertyNode("arrivalTime").addConstraintViolation();
        }
        return valid;
	}
}
