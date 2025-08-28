package com.pix.poc.application.usecase.impl;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.exception.PixInactiveExpcetion;
import com.pix.poc.domain.exception.PixNotFoundException;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidatePixUseCaseImplTest {

    @Mock
    private PixRepository pixRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ValidatePixUseCaseImpl validatePixUseCase;

    private String pixId;
    private Pix activePix;
    private Pix inactivePix;
    private Account account;
    private Document document;

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

        activePix = new Pix.Builder()
                .account(account)
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        inactivePix = new Pix.Builder()
                .account(account)
                .active(false)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();
    }

    @Test
    void validatePix_DeveRetornarPix_QuandoPixAtivoExiste() {
        // Arrange
        when(pixRepository.findById(anyString())).thenReturn(Optional.of(activePix));

        // Act
        Pix result = validatePixUseCase.validatePix(pixId);

        // Assert
        assertNotNull(result);
        assertEquals(activePix, result);
        assertTrue(result.isActive());
        
        verify(pixRepository).findById(pixId);
    }

    @Test
    void validatePix_DeveLancarExcecao_QuandoPixNaoExiste() {
        // Arrange
        when(pixRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        PixNotFoundException exception = assertThrows(
                PixNotFoundException.class,
                () -> validatePixUseCase.validatePix(pixId)
        );

        assertEquals("Nao há valor de pix para os parametro(s) pesquisado(s)", exception.getMessage());
        verify(pixRepository).findById(pixId);
    }

    @Test
    void validatePix_DeveLancarExcecao_QuandoPixInativo() {
        // Arrange
        when(pixRepository.findById(anyString())).thenReturn(Optional.of(inactivePix));

        // Act & Assert
        PixInactiveExpcetion exception = assertThrows(
                PixInactiveExpcetion.class,
                () -> validatePixUseCase.validatePix(pixId)
        );

        assertEquals("Pix desativado. Não é possível realizar alterações", exception.getMessage());
        verify(pixRepository).findById(pixId);
    }

    @Test
    void validatePix_DeveChamarRepositoryComIdCorreto_QuandoExecutado() {
        // Arrange
        when(pixRepository.findById(anyString())).thenReturn(Optional.of(activePix));

        // Act
        validatePixUseCase.validatePix(pixId);

        // Assert
        verify(pixRepository).findById(pixId);
    }

    @Test
    void validatePix_DeveFuncionarComDiferentesIds_QuandoExecutado() {
        // Arrange
        String differentId = "different-pix-id";
        when(pixRepository.findById(anyString())).thenReturn(Optional.of(activePix));

        // Act
        Pix result = validatePixUseCase.validatePix(differentId);

        // Assert
        assertNotNull(result);
        assertEquals(activePix, result);
        verify(pixRepository).findById(differentId);
    }

    @Test
    void validatePix_DeveFuncionarComIdNulo_QuandoExecutado() {
        // Arrange
        when(pixRepository.findById(null)).thenReturn(Optional.empty());

        // Act & Assert
        PixNotFoundException exception = assertThrows(
                PixNotFoundException.class,
                () -> validatePixUseCase.validatePix(null)
        );

        assertEquals("Nao há valor de pix para os parametro(s) pesquisado(s)", exception.getMessage());
        verify(pixRepository).findById(null);
    }

    @Test
    void validatePix_DeveFuncionarComIdVazio_QuandoExecutado() {
        // Arrange
        String emptyId = "";
        when(pixRepository.findById(emptyId)).thenReturn(Optional.empty());

        // Act & Assert
        PixNotFoundException exception = assertThrows(
                PixNotFoundException.class,
                () -> validatePixUseCase.validatePix(emptyId)
        );

        assertEquals("Nao há valor de pix para os parametro(s) pesquisado(s)", exception.getMessage());
        verify(pixRepository).findById(emptyId);
    }

    @Test
    void validatePix_DeveFuncionarComIdComEspacos_QuandoExecutado() {
        // Arrange
        String idWithSpaces = "  test-pix-id  ";
        when(pixRepository.findById(idWithSpaces)).thenReturn(Optional.of(activePix));

        // Act
        Pix result = validatePixUseCase.validatePix(idWithSpaces);

        // Assert
        assertNotNull(result);
        assertEquals(activePix, result);
        verify(pixRepository).findById(idWithSpaces);
    }

    @Test
    void validatePix_DeveFuncionarComIdMuitoLongo_QuandoExecutado() {
        // Arrange
        String longId = "a".repeat(1000);
        when(pixRepository.findById(longId)).thenReturn(Optional.of(activePix));

        // Act
        Pix result = validatePixUseCase.validatePix(longId);

        // Assert
        assertNotNull(result);
        assertEquals(activePix, result);
        verify(pixRepository).findById(longId);
    }

    @Test
    void validatePix_DeveFuncionarComIdComCaracteresEspeciais_QuandoExecutado() {
        // Arrange
        String specialId = "test-pix-id-!@#$%^&*()";
        when(pixRepository.findById(specialId)).thenReturn(Optional.of(activePix));

        // Act
        Pix result = validatePixUseCase.validatePix(specialId);

        // Assert
        assertNotNull(result);
        assertEquals(activePix, result);
        verify(pixRepository).findById(specialId);
    }

    @Test
    void validatePix_DeveRetornarPixComTodosOsCampos_QuandoPixValido() {
        // Arrange
        when(pixRepository.findById(anyString())).thenReturn(Optional.of(activePix));

        // Act
        Pix result = validatePixUseCase.validatePix(pixId);

        // Assert
        assertNotNull(result);
        assertEquals(account, result.getAccount());
        assertTrue(result.isActive());
        assertNotNull(result.getInclusionDate());
        verify(pixRepository).findById(pixId);
    }

    @Test
    void validatePix_DeveVerificarStatusAtivo_QuandoPixExiste() {
        // Arrange
        when(pixRepository.findById(anyString())).thenReturn(Optional.of(activePix));

        // Act
        Pix result = validatePixUseCase.validatePix(pixId);

        // Assert
        assertTrue(result.isActive());
        verify(pixRepository).findById(pixId);
    }
}
