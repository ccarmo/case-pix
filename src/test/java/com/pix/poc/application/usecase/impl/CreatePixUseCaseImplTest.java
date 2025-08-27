
/**
package com.pix.poc.application.usecase.impl;

import com.pix.poc.domain.entities.*;

import com.pix.poc.domain.exception.InvalidMaxValueCnpjException;
import com.pix.poc.domain.exception.InvalidMaxValueCpfException;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.domain.vo.PixValue;
import com.pix.poc.interactors.web.dto.request.CreatePixRequest;
import com.pix.poc.interactors.web.dto.response.SavePixResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePixUseCaseImplTest {

    @Mock
    private PixRepository pixRepository;

    @Mock
    private AccountRepository accountRepository;

    private CreatePixUseCaseImpl createPixUseCase;

    @BeforeEach
    void setUp() {
        createPixUseCase = new CreatePixUseCaseImpl(pixRepository, accountRepository);
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoContaCPFComMenosDe5Pix() {

        Account account = createAccountWithCpfDocument();
        Pix pix = createPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(3L);
        when(pixRepository.save(any(Pix.class)))
                .thenReturn(pix);


        SavePixResponse result = createPixUseCase.createPix(pix);

        verify(accountRepository).getAccountsByDocument(account.getDocument());
        verify(pixRepository).countPixByAccounts(Arrays.asList(account));
        verify(pixRepository).save(pix);
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoContaCNPJComMenosDe5Pix() {

        Account account = createAccountWithCnpjDocument();
        Pix pix = createPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(3L);
        when(pixRepository.save(any(Pix.class)))
                .thenReturn(pix);


        SavePixResponse result = createPixUseCase.createPix(pix);

        verify(accountRepository).getAccountsByDocument(account.getDocument());
        verify(pixRepository).countPixByAccounts(Arrays.asList(account));
        verify(pixRepository).save(pix);
    }

    @Test
    void createPix_DeveLancarExcecao_QuandoContaCPFComMaisDe5Pix() {

        Account account = createAccountWithCpfDocument();
        Pix pix = createPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(6L);

        InvalidMaxValueCpfException exception = assertThrows(
                InvalidMaxValueCpfException.class,
                () -> createPixUseCase.createPix(pix)
        );
        
        assertEquals("Cliente possui mais de 5 pix cadastros para pessoa física", exception.getMessage());
        verify(accountRepository).getAccountsByDocument(account.getDocument());
        verify(pixRepository).countPixByAccounts(Arrays.asList(account));
        verify(pixRepository, never()).save(any(Pix.class));
    }

    @Test
    void createPix_DeveLancarExcecao_QuandoContaCNPJComMaisDe20Pix() {

        Account account = createAccountWithCnpjDocument();
        Pix pix = createPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(21L);

        InvalidMaxValueCnpjException exception = assertThrows(
                InvalidMaxValueCnpjException.class,
                () -> createPixUseCase.createPix(pix)
        );
        
        assertEquals("Cliente possui mais de 20 pix cadastros para pessoa jurídica", exception.getMessage());
        verify(accountRepository).getAccountsByDocument(account.getDocument());
        verify(pixRepository).countPixByAccounts(Arrays.asList(account));
        verify(pixRepository, never()).save(any(Pix.class));
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoContaCPFComExatamente5Pix() {

        Account account = createAccountWithCpfDocument();
        Pix pix = createPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(5L);
        when(pixRepository.save(any(Pix.class)))
                .thenReturn(pix);


        SavePixResponse result = createPixUseCase.createPix(pix);


        verify(accountRepository).getAccountsByDocument(account.getDocument());
        verify(pixRepository).countPixByAccounts(Arrays.asList(account));
        verify(pixRepository).save(pix);
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoContaCNPJComExatamente20Pix() {

        Account account = createAccountWithCnpjDocument();
        Pix pix = createPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(20L);
        when(pixRepository.save(any(Pix.class)))
                .thenReturn(pix);

        SavePixResponse result = createPixUseCase.createPix(pix);

        verify(accountRepository).getAccountsByDocument(account.getDocument());
        verify(pixRepository).countPixByAccounts(Arrays.asList(account));
        verify(pixRepository).save(pix);
    }

    @Test
    void createPix_DeveProcessarMultiplasContas_QuandoDocumentoPossuiVariasContas() {
        Account account1 = createAccountWithCnpjDocument();
        Account account2 = createAccountWithCnpjDocument();
        List<Account> accounts = Arrays.asList(account1, account2);
        Pix pix = createPix(account1);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(accounts);
        when(pixRepository.countPixByAccounts(accounts))
                .thenReturn(3L);
        when(pixRepository.save(any(Pix.class)))
                .thenReturn(pix);


        SavePixResponse result = createPixUseCase.createPix(pix);

        verify(accountRepository).getAccountsByDocument(pix.getAccount().getDocument());
        verify(pixRepository).countPixByAccounts(accounts);
        verify(pixRepository).save(pix);
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoContaCNPJCom19Pix() {
        Account account = createAccountWithCnpjDocument();
        Pix pix = createPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(19L);
        when(pixRepository.save(any(Pix.class)))
                .thenReturn(pix);

        SavePixResponse result = createPixUseCase.createPix(pix);


        verify(accountRepository).getAccountsByDocument(account.getDocument());
        verify(pixRepository).countPixByAccounts(Arrays.asList(account));
        verify(pixRepository).save(pix);
    }

    private Account createAccountWithCpfDocument() {
        Document document = new Document("12345678901");
        return new Account.Builder()
                .document(document)
                .accountType(AccountType.CORRENTE)
                .accountNumber(new AccountNumber(12345))
                .agencyNumber(new AgencyNumber(1234))
                .name("João Silva")
                .lastName("Santos")
                .build();
    }

    private Account createAccountWithCnpjDocument() {
        Document document = new Document("12345678901234");
        return new Account.Builder()
                .document(document)
                .accountType(AccountType.CORRENTE)
                .accountNumber(new AccountNumber(12345))
                .agencyNumber(new AgencyNumber(1234))
                .name("João Silva Cnpj")
                .lastName("Santos Cnpj")
                .build();
    }

    private CreatePixRequest createPixRequest() {
    }

    private Pix createPix(Account account) {
        return new Pix.Builder()
                .account(account)
                .pixType(PixType.EMAIL)
                .pixValue(new PixValue("joao@email.com", PixType.EMAIL))
                .build();
    }
}

*/