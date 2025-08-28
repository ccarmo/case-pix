package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.ValidatePixUseCase;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.interactors.web.dto.response.DeletionPixResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletionUseCaseImplTest {

    @Mock
    private ValidatePixUseCase validatePixUseCase;

    @Mock
    private PixRepository pixRepository;

    @InjectMocks
    private DeletionUseCaseImpl deletionUseCase;

    private String pixId;
    private Pix pix;
    private Account account;
    private Document document;
    private Pix updatedPix;

    @BeforeEach
    void setUp() {
        pixId = "test-pix-id";
        
        document = new Document("12345678909");
        
        account = new Account.Builder()
                .document(document)
                .accountType(AccountType.CORRENTE)
                .accountNumber(new AccountNumber(12345))
                .agencyNumber(new AgencyNumber(1234))
                .build();

        pix = new Pix.Builder()
                .account(account)
                .pixType(PixType.EMAIL) // ou CPF, TELEFONE, etc.
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        updatedPix = new Pix.Builder()
                .account(account)
                .pixType(PixType.EMAIL) // igual
                .active(false)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .inactivationDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();
    }

    @Test
    void deletePix_DeveDesativarPixComSucesso_QuandoIdValido() {
        // Arrange
        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        DeletionPixResponse response = deletionUseCase.deletePix(pixId);

        // Assert
        assertNotNull(response);
        assertFalse(pix.isActive());
        assertNotNull(pix.getInactivationDate());
        
        verify(validatePixUseCase).validatePix(pixId);
        verify(pixRepository).save(pix);
    }

    @Test
    void deletePix_DeveChamarValidatePix_QuandoExecutado() {
        // Arrange
        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        deletionUseCase.deletePix(pixId);

        // Assert
        verify(validatePixUseCase).validatePix(pixId);
    }

    @Test
    void deletePix_DeveAlterarStatusParaInativo_QuandoExecutado() {
        // Arrange
        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        deletionUseCase.deletePix(pixId);

        // Assert
        assertFalse(pix.isActive());
        verify(pixRepository).save(pix);
    }

    @Test
    void deletePix_DeveDefinirDataDeInativacao_QuandoExecutado() {
        // Arrange
        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        deletionUseCase.deletePix(pixId);

        // Assert
        assertNotNull(pix.getInactivationDate());
        verify(pixRepository).save(pix);
    }

    @Test
    void deletePix_DeveSalvarPixAtualizado_QuandoExecutado() {
        // Arrange
        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        deletionUseCase.deletePix(pixId);

        // Assert
        verify(pixRepository).save(pix);
    }

    @Test
    void deletePix_DeveRetornarResponseCorreto_QuandoExecutado() {
        // Arrange
        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        DeletionPixResponse response = deletionUseCase.deletePix(pixId);

        // Assert
        assertNotNull(response);
        // Aqui você pode adicionar mais verificações específicas sobre o response
        // dependendo da implementação do método toDeletionPixResponse
    }

    @Test
    void deletePix_DeveFuncionarComDiferentesIds_QuandoExecutado() {
        // Arrange
        String differentId = "different-pix-id";
        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        DeletionPixResponse response = deletionUseCase.deletePix(differentId);

        // Assert
        assertNotNull(response);
        verify(validatePixUseCase).validatePix(differentId);
        verify(pixRepository).save(pix);
    }

    @Test
    void deletePix_DeveFuncionarComPixJaInativo_QuandoExecutado() {
        // Arrange
        Pix inactivePix = new Pix.Builder()
                .pixType(PixType.EMAIL)
                .account(account)
                .active(false)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        when(validatePixUseCase.validatePix(anyString())).thenReturn(inactivePix);
        when(pixRepository.save(any(Pix.class))).thenReturn(inactivePix);

        // Act
        DeletionPixResponse response = deletionUseCase.deletePix(pixId);

        // Assert
        assertNotNull(response);
        assertFalse(inactivePix.isActive());
        verify(validatePixUseCase).validatePix(pixId);
        verify(pixRepository).save(inactivePix);
    }

    @Test
    void deletePix_DeveDefinirDataDeInativacaoComTimezoneCorreto_QuandoExecutado() {
        // Arrange
        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        deletionUseCase.deletePix(pixId);

        // Assert
        assertNotNull(pix.getInactivationDate());
        assertEquals(ZoneId.of("America/Sao_Paulo"), pix.getInactivationDate().getZone());
        verify(pixRepository).save(pix);
    }

    @Test
    void deletePix_DeveManterOutrosCamposInalterados_QuandoExecutado() {
        // Arrange
        ZonedDateTime originalInclusionDate = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        pix = new Pix.Builder()
                .account(account)
                .active(true)
                .inclusionDate(originalInclusionDate)
                .build();

        when(validatePixUseCase.validatePix(anyString())).thenReturn(pix);
        when(pixRepository.save(any(Pix.class))).thenReturn(updatedPix);

        // Act
        deletionUseCase.deletePix(pixId);

        // Assert
        assertEquals(account, pix.getAccount());
        assertEquals(originalInclusionDate, pix.getInclusionDate());
        assertFalse(pix.isActive());
        assertNotNull(pix.getInactivationDate());
        
        verify(validatePixUseCase).validatePix(pixId);
        verify(pixRepository).save(pix);
    }
}
