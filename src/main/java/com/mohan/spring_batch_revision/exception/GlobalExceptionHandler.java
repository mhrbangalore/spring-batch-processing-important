package com.mohan.spring_batch_revision.exception;

import com.mohan.spring_batch_revision.dao.ErrorMessageDao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageDao> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(buildErrorMessageDao(ex.getMostSpecificCause().toString(),
                HttpStatus.CONFLICT.toString()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileNotPresent.class)
    public ResponseEntity<ErrorMessageDao> handleFileNotFound(FileNotPresent ex) {
        return new ResponseEntity<>(buildErrorMessageDao("File Not Found", HttpStatus.NOT_FOUND.toString()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDao> handleGeneric(Exception ex) {
        return new ResponseEntity<>(buildErrorMessageDao(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString())
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorMessageDao buildErrorMessageDao(String message, String status){
        return ErrorMessageDao
                .builder()
                .message(message)
                .statusCode(status)
                .build();
    }
}
