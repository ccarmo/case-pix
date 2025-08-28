package com.pix.poc.interactors.web.controller;

import com.pix.poc.application.usecase.CreatePixUseCase;
import com.pix.poc.application.usecase.DeletionUseCase;
import com.pix.poc.application.usecase.GetPixUseCase;
import com.pix.poc.application.usecase.UpdateUseCase;
import com.pix.poc.interactors.web.dto.request.CreatePixRequest;
import com.pix.poc.interactors.web.dto.request.PixFilterRequest;
import com.pix.poc.interactors.web.dto.request.UpdatePixRequest;
import com.pix.poc.interactors.web.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PixControllerTest {

    @Mock
    private CreatePixUseCase createPixUseCase;

    @Mock
    private GetPixUseCase getPixUseCase;

    @Mock
    private UpdateUseCase updateUseCase;

    @Mock
    private DeletionUseCase deletionUseCase;

    private PixController pixController;

    @BeforeEach
    void setUp() {
        pixController = new PixController(createPixUseCase, getPixUseCase, updateUseCase, deletionUseCase);
    }

    @Test
    void create_DeveRetornarPixResponseComSucesso_QuandoPixCriado() {
        // Arrange
        CreatePixRequest request = new CreatePixRequest(
                "12345678901",
                12345,
                "CORRENTE",
                1234,
                "João",
                "Silva",
                "EMAIL",
                "joao@email.com"
        );

        SavePixResponse saveResponse = new SavePixResponse("pix-123");
        when(createPixUseCase.createPix(any(CreatePixRequest.class))).thenReturn(saveResponse);

        // Act
        PixResponse<String> response = pixController.create(request);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseType.SUCCESS, response.getType());
        assertEquals(1, response.getResult().size());
        assertEquals("pix-123", response.getResult().get(0));
        verify(createPixUseCase, times(1)).createPix(request);
    }

    @Test
    void get_DeveRetornarPixResponseComListaPix_QuandoFiltrosValidos() {
        // Arrange
        PixFilterRequest filterRequest = new PixFilterRequest(
                "123",
                "EMAIL",
                1234,
                12345,
                "João Silva",
                LocalDate.now(),
                null
        );

        GetPixResponse getPixResponse = new GetPixResponse(
                "pix-123",
                "EMAIL",
                1234,
                12345,
                "João Silva",
                "01/01/2024",
                null
        );

        when(getPixUseCase.getPix(any(PixFilterRequest.class))).thenReturn(List.of(getPixResponse));

        // Act
        PixResponse<GetPixResponse> response = pixController.get(filterRequest);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseType.SUCCESS, response.getType());
        assertEquals(1, response.getResult().size());
        assertEquals(getPixResponse, response.getResult().get(0));
        verify(getPixUseCase, times(1)).getPix(filterRequest);
    }

    @Test
    void get_DeveRetornarPixResponseComListaVazia_QuandoNenhumPixEncontrado() {
        // Arrange
        PixFilterRequest filterRequest = new PixFilterRequest(
                "999",
                "EMAIL",
                9999,
                99999,
                "Nome Inexistente",
                LocalDate.now(),
                null
        );

        when(getPixUseCase.getPix(any(PixFilterRequest.class))).thenReturn(List.of());

        // Act
        PixResponse<GetPixResponse> response = pixController.get(filterRequest);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseType.SUCCESS, response.getType());
        assertTrue(response.getResult().isEmpty());
        verify(getPixUseCase, times(1)).getPix(filterRequest);
    }

    @Test
    void update_DeveRetornarPixResponseComSucesso_QuandoPixAtualizado() {
        // Arrange
        UpdatePixRequest request = new UpdatePixRequest(
                "pix-123",
                54321,
                "POUPANCA",
                4321,
                "João",
                "Silva"
        );

        UpdatePixResponse updateResponse = new UpdatePixResponse(
                "pix-123",
                "POUPANCA",
                4321,
                54321,
                "João",
                "Silva"
        );
        when(updateUseCase.changePix(any(UpdatePixRequest.class))).thenReturn(updateResponse);

        // Act
        PixResponse<UpdatePixResponse> response = pixController.update(request);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseType.SUCCESS, response.getType());
        assertEquals(1, response.getResult().size());
        assertEquals(updateResponse, response.getResult().get(0));
        verify(updateUseCase, times(1)).changePix(request);
    }

    @Test
    void delete_DeveRetornarPixResponseComSucesso_QuandoPixDeletado() {
        // Arrange
        String pixId = "pix-123";
        DeletionPixResponse deletionResponse = new DeletionPixResponse(
                "pix-123",
                "EMAIL",
                1234,
                12345,
                "João Silva",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        when(deletionUseCase.deletePix(anyString())).thenReturn(deletionResponse);

        // Act
        PixResponse<DeletionPixResponse> response = pixController.delete(pixId);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseType.SUCCESS, response.getType());
        assertEquals(1, response.getResult().size());
        assertEquals(deletionResponse, response.getResult().get(0));
        verify(deletionUseCase, times(1)).deletePix(pixId);
    }

    @Test
    void create_DeveChamarCreatePixUseCase_QuandoExecutado() {
        // Arrange
        CreatePixRequest request = new CreatePixRequest(
                "12345678901",
                12345,
                "CORRENTE",
                1234,
                "João",
                "Silva",
                "EMAIL",
                "joao@email.com"
        );

        SavePixResponse saveResponse = new SavePixResponse("pix-123");
        when(createPixUseCase.createPix(any(CreatePixRequest.class))).thenReturn(saveResponse);

        // Act
        pixController.create(request);

        // Assert
        verify(createPixUseCase, times(1)).createPix(request);
    }

    @Test
    void get_DeveChamarGetPixUseCase_QuandoExecutado() {
        // Arrange
        PixFilterRequest filterRequest = new PixFilterRequest(
                "123",
                "EMAIL",
                1234,
                12345,
                "João Silva",
                LocalDate.now(),
                null
        );

        when(getPixUseCase.getPix(any(PixFilterRequest.class))).thenReturn(List.of());

        // Act
        pixController.get(filterRequest);

        // Assert
        verify(getPixUseCase, times(1)).getPix(filterRequest);
    }

    @Test
    void update_DeveChamarUpdateUseCase_QuandoExecutado() {
        // Arrange
        UpdatePixRequest request = new UpdatePixRequest(
                "pix-123",
                54321,
                "POUPANCA",
                4321,
                "João",
                "Silva"
        );

        UpdatePixResponse updateResponse = new UpdatePixResponse(
                "pix-123",
                "POUPANCA",
                4321,
                54321,
                "João",
                "Silva"
        );
        when(updateUseCase.changePix(any(UpdatePixRequest.class))).thenReturn(updateResponse);

        // Act
        pixController.update(request);

        // Assert
        verify(updateUseCase, times(1)).changePix(request);
    }

    @Test
    void delete_DeveChamarDeletionUseCase_QuandoExecutado() {
        // Arrange
        String pixId = "pix-123";
        DeletionPixResponse deletionResponse = new DeletionPixResponse(
                "pix-123",
                "EMAIL",
                1234,
                12345,
                "João Silva",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        when(deletionUseCase.deletePix(anyString())).thenReturn(deletionResponse);

        // Act
        pixController.delete(pixId);

        // Assert
        verify(deletionUseCase, times(1)).deletePix(pixId);
    }

    @Test
    void create_DeveRetornarResponseComIdCorreto_QuandoExecutado() {
        // Arrange
        CreatePixRequest request = new CreatePixRequest(
                "12345678901",
                12345,
                "CORRENTE",
                1234,
                "João",
                "Silva",
                "EMAIL",
                "joao@email.com"
        );

        SavePixResponse saveResponse = new SavePixResponse("pix-456");
        when(createPixUseCase.createPix(any(CreatePixRequest.class))).thenReturn(saveResponse);

        // Act
        PixResponse<String> response = pixController.create(request);

        // Assert
        assertEquals("pix-456", response.getResult().get(0));
        verify(createPixUseCase, times(1)).createPix(request);
    }

    @Test
    void get_DeveRetornarResponseComDadosCorretos_QuandoExecutado() {
        // Arrange
        PixFilterRequest filterRequest = new PixFilterRequest(
                "123",
                "EMAIL",
                1234,
                12345,
                "João Silva",
                LocalDate.now(),
                null
        );

        GetPixResponse getPixResponse = new GetPixResponse(
                "pix-789",
                "EMAIL",
                1234,
                12345,
                "João Silva",
                "01/01/2024",
                null
        );

        when(getPixUseCase.getPix(any(PixFilterRequest.class))).thenReturn(List.of(getPixResponse));

        // Act
        PixResponse<GetPixResponse> response = pixController.get(filterRequest);

        // Assert
        assertEquals(getPixResponse, response.getResult().get(0));
        verify(getPixUseCase, times(1)).getPix(filterRequest);
    }
}
