package com.pix.poc.interactors.web.exception;

import com.pix.poc.domain.exception.*;

import com.pix.poc.interactors.web.dto.response.PixResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PixResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> reasons = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(PixResponse.error(reasons.getFirst()));
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<PixResponse> handleValidationException(ValidationException ex) {
        log.warn("Validation error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(PixResponse.error(ex.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<PixResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(PixResponse.error("Erro inesperado. Tente novamente mais tarde."));
    }

    @ExceptionHandler(PixNotFoundException.class)
    public ResponseEntity<PixResponse> handleNotFound(PixNotFoundException ex) {
        log.warn("Recurso não encontrado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(PixResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidMaxValueCpfException.class)
    public ResponseEntity<PixResponse> handleMaxValueCpf(InvalidMaxValueCpfException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(PixResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(PixInactiveExpcetion.class)
    public ResponseEntity<PixResponse> handleMaxValueCpf(PixInactiveExpcetion ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(PixResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidPixValueException.class)
    public ResponseEntity<PixResponse> handleMaxValueCpf(InvalidPixValueException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(PixResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<PixResponse> handleAccount(AccountNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(PixResponse.error(ex.getMessage()));
    }
}
