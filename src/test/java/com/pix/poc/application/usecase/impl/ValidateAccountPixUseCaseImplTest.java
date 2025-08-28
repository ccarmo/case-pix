package com.pix.poc.application.usecase.impl;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.exception.AccountNotFoundException;
import com.pix.poc.domain.repository.AccountRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateAccountPixUseCaseImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ValidateAccountPixUseCaseImpl validateAccountPixUseCase;

    private AccountNumber accountNumber;
    private AgencyNumber agencyNumber;
    private Account account;
    private Document document;

    @BeforeEach
    void setUp() {
        accountNumber = new AccountNumber(12345);
        agencyNumber = new AgencyNumber(1234);
        
        document = new Document("12345678909");
        
        account = new Account.Builder()
                .document(document)
                .accountType(AccountType.CORRENTE)
                .accountNumber(accountNumber)
                .agencyNumber(agencyNumber)
                .build();
    }

    @Test
    void validateAccount_DeveRetornarConta_QuandoContaExiste() {
        // Arrange
        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(account));

        // Act
        Account result = validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        // Assert
        assertNotNull(result);
        assertEquals(account, result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(agencyNumber, result.getAgencyNumber());
        
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveLancarExcecao_QuandoContaNaoExiste() {
        // Arrange
        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber)
        );

        assertEquals("Conta e Agência enviada para alteração não existe.", exception.getMessage());
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveChamarRepositoryComParametrosCorretos_QuandoExecutado() {
        // Arrange
        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(account));

        // Act
        validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        // Assert
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveFuncionarComDiferentesNumerosDeConta_QuandoExecutado() {
        // Arrange
        AccountNumber differentAccountNumber = new AccountNumber(99999);
        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(account));

        // Act
        Account result = validateAccountPixUseCase.validateAccount(differentAccountNumber, agencyNumber);

        // Assert
        assertNotNull(result);
        assertEquals(account, result);
        verify(accountRepository).findByAccountNumberAndAgencyNumber(differentAccountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveFuncionarComDiferentesNumerosDeAgencia_QuandoExecutado() {
        // Arrange
        AgencyNumber differentAgencyNumber = new AgencyNumber(9999);
        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(account));

        // Act
        Account result = validateAccountPixUseCase.validateAccount(accountNumber, differentAgencyNumber);

        // Assert
        assertNotNull(result);
        assertEquals(account, result);
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, differentAgencyNumber);
    }

    @Test
    void validateAccount_DeveFuncionarComDiferentesTiposDeConta_QuandoExecutado() {
        // Arrange
        Account poupancaAccount = new Account.Builder()
                .document(document)
                .accountType(AccountType.POUPANCA)
                .accountNumber(accountNumber)
                .agencyNumber(agencyNumber)
                .build();

        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(poupancaAccount));

        // Act
        Account result = validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        // Assert
        assertNotNull(result);
        assertEquals(AccountType.POUPANCA, result.getAccountType());
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveFuncionarComDiferentesDocumentos_QuandoExecutado() {
        // Arrange
        Document differentDocument = new Document("12345678909");
        Account differentDocumentAccount = new Account.Builder()
                .document(differentDocument)
                .accountType(AccountType.CORRENTE)
                .accountNumber(accountNumber)
                .agencyNumber(agencyNumber)
                .build();

        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(differentDocumentAccount));

        // Act
        Account result = validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        // Assert
        assertNotNull(result);
        assertEquals(differentDocument, result.getDocument());
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveFuncionarComContaCorrente_QuandoExecutado() {
        // Arrange
        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(account));

        // Act
        Account result = validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        // Assert
        assertNotNull(result);
        assertEquals(AccountType.CORRENTE, result.getAccountType());
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveFuncionarComContaPoupanca_QuandoExecutado() {
        // Arrange
        Account poupancaAccount = new Account.Builder()
                .document(document)
                .accountType(AccountType.POUPANCA)
                .accountNumber(accountNumber)
                .agencyNumber(agencyNumber)
                .build();

        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(poupancaAccount));

        // Act
        Account result = validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        // Assert
        assertNotNull(result);
        assertEquals(AccountType.POUPANCA, result.getAccountType());
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveRetornarContaComTodosOsCampos_QuandoContaValida() {
        // Arrange
        when(accountRepository.findByAccountNumberAndAgencyNumber(any(AccountNumber.class), any(AgencyNumber.class)))
                .thenReturn(Optional.of(account));

        // Act
        Account result = validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        // Assert
        assertNotNull(result);
        assertEquals(document, result.getDocument());
        assertEquals(AccountType.CORRENTE, result.getAccountType());
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(agencyNumber, result.getAgencyNumber());
        verify(accountRepository).findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
    }

    @Test
    void validateAccount_DeveFuncionarComParametrosNulos_QuandoExecutado() {
        // Arrange
        when(accountRepository.findByAccountNumberAndAgencyNumber(null, null))
                .thenReturn(Optional.empty());

        // Act & Assert
        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> validateAccountPixUseCase.validateAccount(null, null)
        );

        assertEquals("Conta e Agência enviada para alteração não existe.", exception.getMessage());
        verify(accountRepository).findByAccountNumberAndAgencyNumber(null, null);
    }

    @Test
    void validateAccount_DeveFuncionarComParametrosVazios_QuandoExecutado() {
        // Arrange
        AccountNumber emptyAccountNumber = new AccountNumber(0);
        AgencyNumber emptyAgencyNumber = new AgencyNumber(0);
        when(accountRepository.findByAccountNumberAndAgencyNumber(emptyAccountNumber, emptyAgencyNumber))
                .thenReturn(Optional.empty());

        // Act & Assert
        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> validateAccountPixUseCase.validateAccount(emptyAccountNumber, emptyAgencyNumber)
        );

        assertEquals("Conta e Agência enviada para alteração não existe.", exception.getMessage());
        verify(accountRepository).findByAccountNumberAndAgencyNumber(emptyAccountNumber, emptyAgencyNumber);
    }
}
