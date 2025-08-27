package com.pix.poc.interactors.web.exception;

import com.pix.poc.domain.exception.InvalidMaxValueCpfException;
import com.pix.poc.domain.exception.PixInactiveExpcetion;
import com.pix.poc.domain.exception.PixNotFoundException;
import com.pix.poc.domain.exception.ValidationException;

import com.pix.poc.interactors.web.dto.response.ResponsePixCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponsePixCustom> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> reasons = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ResponsePixCustom.error(reasons.getFirst()));
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponsePixCustom> handleValidationException(ValidationException ex) {
        log.warn("Validation error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ResponsePixCustom.error(ex.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePixCustom> handleGeneric(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponsePixCustom.error("Erro inesperado. Tente novamente mais tarde."));
    }

    @ExceptionHandler(PixNotFoundException.class)
    public ResponseEntity<ResponsePixCustom> handleNotFound(PixNotFoundException ex) {
        log.warn("Recurso não encontrado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponsePixCustom.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidMaxValueCpfException.class)
    public ResponseEntity<ResponsePixCustom> handleMaxValueCpf(InvalidMaxValueCpfException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ResponsePixCustom.error(ex.getMessage()));
    }

    @ExceptionHandler(PixInactiveExpcetion.class)
    public ResponseEntity<ResponsePixCustom> handleMaxValueCpf(PixInactiveExpcetion ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ResponsePixCustom.error(ex.getMessage()));
    }
}
