package com.pix.poc.application.usecase.impl;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.DocumentType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.exception.PixNotFoundException;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.domain.vo.PixValue;
import com.pix.poc.interactors.web.dto.request.PixFilterRequest;
import com.pix.poc.interactors.web.dto.response.GetPixResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPixUseCaseImplTest {

    @Mock
    private PixRepository pixRepository;

    private GetPixUseCaseImpl getPixUseCase;

    @BeforeEach
    void setUp() {
        getPixUseCase = new GetPixUseCaseImpl(pixRepository);
    }

    @Test
    void getPix_DeveRetornarListaPix_QuandoFiltrosRetornamResultados() {
        // Arrange
        PixFilterRequest request = createPixFilterRequest();
        List<Pix> pixList = Arrays.asList(
                createPix("pix1", "joao@email.com"),
                createPix("pix2", "maria@email.com")
        );
        
        when(pixRepository.get(
                eq("123"),
                eq("EMAIL"),
                eq("123"),
                eq(1234),
                eq(12345),
                eq("João Silva"),
                eq(LocalDate.of(2024, 1, 1)),
                eq(LocalDate.of(2024, 12, 31))
        )).thenReturn(pixList);

        // Act
        List<GetPixResponse> result = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        GetPixResponse firstPix = result.get(0);
        assertEquals("pix1", firstPix.id());
        assertEquals("EMAIL", firstPix.pixType());
        assertEquals(1234, firstPix.agencyNumber());
        assertEquals(12345, firstPix.accountNumber());
        assertEquals("João Silva", firstPix.name());
        
        verify(pixRepository).get(
                "123", "EMAIL", "123", 1234, 12345, 
                "João Silva", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)
        );
    }

    @Test
    void getPix_DeveRetornarListaPix_QuandoFiltrosMinimosSaoFornecidos() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                "123", "EMAIL", 1234, 12345, "João Silva", null, null
        );
        List<Pix> pixList = Arrays.asList(createPix("pix1", "joao@email.com"));
        
        when(pixRepository.get(
                eq("123"), eq("EMAIL"), eq("123"), eq(1234), eq(12345), 
                eq("João Silva"), eq(null), eq(null)
        )).thenReturn(pixList);

        // Act
        List<GetPixResponse> result = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pixRepository).get(
                "123", "EMAIL", "123", 1234, 12345, "João Silva", null, null
        );
    }

    @Test
    void getPix_DeveRetornarListaPix_QuandoApenasIdEhFornecido() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                "123", null, null, null, null, null, null
        );
        List<Pix> pixList = Arrays.asList(createPix("123", "joao@email.com"));
        
        when(pixRepository.get(
                eq("123"), eq(null), eq("123"), eq(null), eq(null), 
                eq(null), eq(null), eq(null)
        )).thenReturn(pixList);

        // Act
        List<GetPixResponse> result = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pixRepository).get(
                "123", null, "123", null, null, null, null, null
        );
    }

    @Test
    void getPix_DeveRetornarListaPix_QuandoApenasPixTypeEhFornecido() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                null, "EMAIL", null, null, null, null, null
        );
        List<Pix> pixList = Arrays.asList(
                createPix("pix1", "joao@email.com"),
                createPix("pix2", "maria@email.com")
        );
        
        when(pixRepository.get(
                eq(null), eq("EMAIL"), eq(null), eq(null), eq(null), 
                eq(null), eq(null), eq(null)
        )).thenReturn(pixList);

        // Act
        List<GetPixResponse> result = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(pixRepository).get(
                null, "EMAIL", null, null, null, null, null, null
        );
    }

    @Test
    void getPix_DeveRetornarListaPix_QuandoApenasAgenciaEhContaEhFornecido() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                null, null, 1234, 12345, null, null, null
        );
        List<Pix> pixList = Arrays.asList(createPix("pix1", "joao@email.com"));
        
        when(pixRepository.get(
                eq(null), eq(null), eq(null), eq(1234), eq(12345), 
                eq(null), eq(null), eq(null)
        )).thenReturn(pixList);

        // Act
        List<GetPixResponse> result = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pixRepository).get(
                null, null, null, 1234, 12345, null, null, null
        );
    }

    @Test
    void getPix_DeveRetornarListaPix_QuandoApenasNomeEhFornecido() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                null, null, null, null, "João Silva", null, null
        );
        List<Pix> pixList = Arrays.asList(createPix("pix1", "joao@email.com"));
        
        when(pixRepository.get(
                eq(null), eq(null), eq(null), eq(null), eq(null), 
                eq("João Silva"), eq(null), eq(null)
        )).thenReturn(pixList);

        // Act
        List<GetPixResponse> result = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pixRepository).get(
                null, null, null, null, null, "João Silva", null, null
        );
    }

    @Test
    void getPix_DeveRetornarListaPix_QuandoApenasDataInclusaoEhFornecido() {
        // Arrange
        LocalDate dataInclusao = LocalDate.of(2024, 1, 1);
        PixFilterRequest request = new PixFilterRequest(
                null, null, null, null, null, dataInclusao, null
        );
        List<Pix> pixList = Arrays.asList(createPix("pix1", "joao@email.com"));
        
        when(pixRepository.get(
                eq(null), eq(null), eq(null), eq(null), eq(null), 
                eq(null), eq(dataInclusao), eq(null)
        )).thenReturn(pixList);

        // Act
        List<GetPixResponse> result = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pixRepository).get(
                null, null, null, null, null, null, dataInclusao, null
        );
    }

    @Test
    void getPix_DeveRetornarListaPix_QuandoApenasDataDesativacaoEhFornecido() {
        // Arrange
        LocalDate dataDesativacao = LocalDate.of(2024, 12, 31);
        PixFilterRequest request = new PixFilterRequest(
                null, null, null, null, null, null, dataDesativacao
        );
        List<Pix> pixList = Arrays.asList(createPix("pix1", "joao@email.com"));
        
        when(pixRepository.get(
                eq(null), eq(null), eq(null), eq(null), eq(null), 
                eq(null), eq(null), eq(dataDesativacao)
        )).thenReturn(pixList);

        // Act
        List<GetPixResponse> result = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pixRepository).get(
                null, null, null, null, null, null, null, dataDesativacao
        );
    }

    @Test
    void getPix_DeveLancarExcecao_QuandoNenhumResultadoEhEncontrado() {
        // Arrange
        PixFilterRequest request = createPixFilterRequest();
        
        when(pixRepository.get(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        PixNotFoundException exception = assertThrows(
                PixNotFoundException.class,
                () -> getPixUseCase.getPix(request)
        );
        
        assertEquals("Nao há valor de pix para os parametro(s) pesquisado(s)", exception.getMessage());
        verify(pixRepository).get(
                "123", "EMAIL", "123", 1234, 12345, 
                "João Silva", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)
        );
    }

    @Test
    void getPix_DeveLancarExcecao_QuandoListaRetornadaEhNull() {
        // Arrange
        PixFilterRequest request = createPixFilterRequest();
        
        when(pixRepository.get(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(null);

        // Act & Assert
        PixNotFoundException exception = assertThrows(
                PixNotFoundException.class,
                () -> getPixUseCase.getPix(request)
        );
        
        assertEquals("Nao há valor de pix para os parametro(s) pesquisado(s)", exception.getMessage());
        verify(pixRepository).get(
                "123", "EMAIL", "123", 1234, 12345, 
                "João Silva", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)
        );
    }

    @Test
    void getPix_DeveRetornarListaVazia_QuandoFiltrosNaoRetornamResultados() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                "999", "TELEFONE", 9999, 99999, "Nome Inexistente", null, null
        );
        
        when(pixRepository.get(
                eq("999"), eq("TELEFONE"), eq("999"), eq(9999), eq(99999), 
                eq("Nome Inexistente"), eq(null), eq(null)
        )).thenReturn(Collections.emptyList());

        // Act & Assert
        PixNotFoundException exception = assertThrows(
                PixNotFoundException.class,
                () -> getPixUseCase.getPix(request)
        );
        
        assertEquals("Nao há valor de pix para os parametro(s) pesquisado(s)", exception.getMessage());
        verify(pixRepository).get(
                "999", "TELEFONE", "999", 9999, 99999, "Nome Inexistente", null, null
        );
    }

    private PixFilterRequest createPixFilterRequest() {
        return new PixFilterRequest(
                "123",
                "EMAIL",
                1234,
                12345,
                "João Silva",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );
    }

    private Pix createPix(String id, String pixValue) {
        Account account = new Account.Builder()
                .document(new Document("12345678901"))
                .accountType(AccountType.CORRENTE)
                .accountNumber(new AccountNumber(12345))
                .agencyNumber(new AgencyNumber(1234))
                .name("João Silva")
                .lastName("Santos")
                .build();

        return new Pix.Builder()
                .uniqueID(id)
                .account(account)
                .pixType(PixType.EMAIL)
                .pixValue(new PixValue(pixValue , PixType.EMAIL))
                .inclusionDate(LocalDate.of(2024, 1, 1))
                .build();
    }
} 