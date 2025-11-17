package com.flightapp.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArrivalTimeValidator.class)
public @interface ValidArrivalTime {
	String message() default "arrivalTime must be AFTER departureTime";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
