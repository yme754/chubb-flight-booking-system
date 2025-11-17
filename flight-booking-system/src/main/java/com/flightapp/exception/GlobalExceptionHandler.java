package com.flightapp.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        errorList.forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errorMap.put(fieldName, message);
        });
        return errorMap;
    }
	private static final String MESSAGE = "message";
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleNotFound(ResourceNotFoundException exc) {
        Map<String, String> m = new HashMap<>();
        m.put(MESSAGE, exc.getMessage());
        return m;
    }
    @ExceptionHandler(BadRequestException.class)
    public Map<String, String> handleBadRequest(BadRequestException exc) {
        Map<String, String> mp = new HashMap<>();
        mp.put(MESSAGE, exc.getMessage());
        return mp;
    }
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleAll(Exception ex) {
        Map<String, String> mp = new HashMap<>();
        mp.put(MESSAGE, "Internal Server Error");
        return mp;
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound1(ResourceNotFoundException ex) {
    	return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
