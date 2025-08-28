package com.pix.poc.interactors.web.exception;

import com.pix.poc.domain.exception.*;
import com.pix.poc.interactors.web.dto.response.PixResponse;
import com.pix.poc.interactors.web.dto.response.ResponseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private BindingResult bindingResult;

    @Mock
    private FieldError fieldError;

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidationErrors_DeveRetornarUnprocessableEntity_QuandoMethodArgumentNotValidException() {
        // Arrange
        String errorMessage = "Campo obrigatório";
        when(fieldError.getDefaultMessage()).thenReturn(errorMessage);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleValidationErrors(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals(errorMessage, response.getBody().getReasons().get(0));
    }

    @Test
    void handleValidationErrors_DeveRetornarPrimeiraMensagemDeErro_QuandoMultiplosErros() {
        // Arrange
        FieldError fieldError1 = mock(FieldError.class);
        FieldError fieldError2 = mock(FieldError.class);
        
        when(fieldError1.getDefaultMessage()).thenReturn("Primeiro erro");
        when(fieldError2.getDefaultMessage()).thenReturn("Segundo erro");
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleValidationErrors(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals("Primeiro erro", response.getBody().getReasons().get(0));
    }

    @Test
    void handleValidationException_DeveRetornarUnprocessableEntity_QuandoValidationException() {
        // Arrange
        String errorMessage = "Erro de validação";
        ValidationException ex = new ValidationException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleValidationException(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals(errorMessage, response.getBody().getReasons().get(0));
    }

    @Test
    void handleGeneric_DeveRetornarInternalServerError_QuandoExceptionGenerica() {
        // Arrange
        String errorMessage = "Erro inesperado";
        Exception ex = new RuntimeException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleGeneric(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals("Erro inesperado. Tente novamente mais tarde.", response.getBody().getReasons().get(0));
    }

    @Test
    void handleNotFound_DeveRetornarNotFound_QuandoPixNotFoundException() {
        // Arrange
        String errorMessage = "Pix não encontrado";
        PixNotFoundException ex = new PixNotFoundException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleNotFound(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals(errorMessage, response.getBody().getReasons().get(0));
    }

    @Test
    void handleMaxValueCpf_DeveRetornarUnprocessableEntity_QuandoInvalidMaxValueCpfException() {
        // Arrange
        String errorMessage = "CPF inválido";
        InvalidMaxValueCpfException ex = new InvalidMaxValueCpfException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleMaxValueCpf(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals(errorMessage, response.getBody().getReasons().get(0));
    }

    @Test
    void handleMaxValueCpf_DeveRetornarUnprocessableEntity_QuandoPixInactiveExpcetion() {
        // Arrange
        String errorMessage = "Pix inativo";
        PixInactiveExpcetion ex = new PixInactiveExpcetion(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleMaxValueCpf(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals(errorMessage, response.getBody().getReasons().get(0));
    }

    @Test
    void handleMaxValueCpf_DeveRetornarUnprocessableEntity_QuandoInvalidPixValueException() {
        // Arrange
        String errorMessage = "Valor do Pix inválido";
        InvalidPixValueException ex = new InvalidPixValueException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleMaxValueCpf(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals(errorMessage, response.getBody().getReasons().get(0));
    }

    @Test
    void handleAccount_DeveRetornarUnprocessableEntity_QuandoAccountNotFoundException() {
        // Arrange
        String errorMessage = "Conta não encontrada";
        AccountNotFoundException ex = new AccountNotFoundException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleAccount(ex);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseType.ERROR, response.getBody().getType());
        assertEquals(errorMessage, response.getBody().getReasons().get(0));
    }

    @Test
    void handleValidationErrors_DeveRetornarResponseComCorpoCorreto_QuandoExecutado() {
        // Arrange
        String errorMessage = "Campo obrigatório";
        when(fieldError.getDefaultMessage()).thenReturn(errorMessage);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleValidationErrors(ex);

        // Assert
        assertNotNull(response.getBody());
        PixResponse<String> body = response.getBody();
        assertEquals(ResponseType.ERROR, body.getType());
        assertEquals(errorMessage, body.getReasons().get(0));
        assertTrue(body.getResult().isEmpty());
    }

    @Test
    void handleValidationException_DeveRetornarResponseComCorpoCorreto_QuandoExecutado() {
        // Arrange
        String errorMessage = "Erro de validação específico";
        ValidationException ex = new ValidationException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleValidationException(ex);

        // Assert
        assertNotNull(response.getBody());
        PixResponse<String> body = response.getBody();
        assertEquals(ResponseType.ERROR, body.getType());
        assertEquals(errorMessage, body.getReasons().get(0));
        assertTrue(body.getResult().isEmpty());
    }

    @Test
    void handleGeneric_DeveRetornarResponseComCorpoCorreto_QuandoExecutado() {
        // Arrange
        Exception ex = new IllegalStateException("Estado ilegal");

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleGeneric(ex);

        // Assert
        assertNotNull(response.getBody());
        PixResponse<String> body = response.getBody();
        assertEquals(ResponseType.ERROR, body.getType());
        assertEquals("Erro inesperado. Tente novamente mais tarde.", body.getReasons().get(0));
        assertTrue(body.getResult().isEmpty());
    }

    @Test
    void handleNotFound_DeveRetornarResponseComCorpoCorreto_QuandoExecutado() {
        // Arrange
        String errorMessage = "Recurso não encontrado";
        PixNotFoundException ex = new PixNotFoundException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleNotFound(ex);

        // Assert
        assertNotNull(response.getBody());
        PixResponse<String> body = response.getBody();
        assertEquals(ResponseType.ERROR, body.getType());
        assertEquals(errorMessage, body.getReasons().get(0));
        assertTrue(body.getResult().isEmpty());
    }

    @Test
    void handleMaxValueCpf_DeveRetornarResponseComCorpoCorreto_QuandoInvalidMaxValueCpfException() {
        // Arrange
        String errorMessage = "CPF com valor máximo excedido";
        InvalidMaxValueCpfException ex = new InvalidMaxValueCpfException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleMaxValueCpf(ex);

        // Assert
        assertNotNull(response.getBody());
        PixResponse<String> body = response.getBody();
        assertEquals(ResponseType.ERROR, body.getType());
        assertEquals(errorMessage, body.getReasons().get(0));
        assertTrue(body.getResult().isEmpty());
    }

    @Test
    void handleMaxValueCpf_DeveRetornarResponseComCorpoCorreto_QuandoPixInactiveExpcetion() {
        // Arrange
        String errorMessage = "Pix está inativo";
        PixInactiveExpcetion ex = new PixInactiveExpcetion(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleMaxValueCpf(ex);

        // Assert
        assertNotNull(response.getBody());
        PixResponse<String> body = response.getBody();
        assertEquals(ResponseType.ERROR, body.getType());
        assertEquals(errorMessage, body.getReasons().get(0));
        assertTrue(body.getResult().isEmpty());
    }

    @Test
    void handleMaxValueCpf_DeveRetornarResponseComCorpoCorreto_QuandoInvalidPixValueException() {
        // Arrange
        String errorMessage = "Valor do Pix é inválido";
        InvalidPixValueException ex = new InvalidPixValueException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleMaxValueCpf(ex);

        // Assert
        assertNotNull(response.getBody());
        PixResponse<String> body = response.getBody();
        assertEquals(ResponseType.ERROR, body.getType());
        assertEquals(errorMessage, body.getReasons().get(0));
        assertTrue(body.getResult().isEmpty());
    }

    @Test
    void handleAccount_DeveRetornarResponseComCorpoCorreto_QuandoAccountNotFoundException() {
        // Arrange
        String errorMessage = "Conta bancária não encontrada";
        AccountNotFoundException ex = new AccountNotFoundException(errorMessage);

        // Act
        ResponseEntity<PixResponse> response = exceptionHandler.handleAccount(ex);

        // Assert
        assertNotNull(response.getBody());
        PixResponse<String> body = response.getBody();
        assertEquals(ResponseType.ERROR, body.getType());
        assertEquals(errorMessage, body.getReasons().get(0));
        assertTrue(body.getResult().isEmpty());
    }
}
