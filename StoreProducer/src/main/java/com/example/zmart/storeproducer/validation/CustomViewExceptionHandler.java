package com.example.zmart.storeproducer.validation;

import com.example.zmart.storeproducer.validation.violation.Violation;
import com.example.zmart.storeproducer.validation.violation.ViolationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestController
@ControllerAdvice
public class CustomViewExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity onConstraintValidationException(
            ConstraintViolationException constraintViolationException) {

        ViolationResponse error = new ViolationResponse();
        Set<ConstraintViolation<?>> constraintViolations =
                constraintViolationException.getConstraintViolations();
        constraintViolations.forEach(
                violation -> {
                    Violation responseViolation = new Violation();
                    responseViolation.setFieldName(violation.getPropertyPath().toString());
                    responseViolation.setMessage(violation.getMessage());
                    error.getViolations().add(responseViolation);
                });

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity onMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {

        ViolationResponse error = new ViolationResponse();
        List<FieldError> fieldErrors =
                methodArgumentNotValidException.getBindingResult().getFieldErrors();
        fieldErrors.forEach(
                fieldError -> {
                    Violation responseViolation = new Violation();
                    responseViolation.setFieldName(fieldError.getField());
                    responseViolation.setMessage(fieldError.getDefaultMessage());
                    error.getViolations().add(responseViolation);
                });

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
