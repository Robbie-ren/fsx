package com.fsx.config;

import com.fsx.exception.*;
import com.fsx.utility.ResponseUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseUtility> handleResourceNotFoundException(ResourceNotFoundException e){
        return ResponseEntity.badRequest().body(new ResponseUtility(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        for(FieldError error : errors){
            map.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(InvalidOwnershipException.class)
    public ResponseEntity<ResponseUtility> handleInvalidOwnershipException(InvalidOwnershipException e){
        return ResponseEntity.badRequest().body(new ResponseUtility(e.getMessage()));
    }

    @ExceptionHandler(InvalidSeatException.class)
    public ResponseEntity<ResponseUtility> handleInvalidSeatException(InvalidSeatException e){
        return ResponseEntity.badRequest().body(new ResponseUtility(e.getMessage()));
    }

    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<ResponseUtility> handleInvalidBookingException(InvalidBookingException e){
        return ResponseEntity.badRequest().body(new ResponseUtility(e.getMessage()));
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<ResponseUtility> handleInvalidPaymentException(InvalidPaymentException e){
        return ResponseEntity.badRequest().body(new ResponseUtility(e.getMessage()));
    }
}
