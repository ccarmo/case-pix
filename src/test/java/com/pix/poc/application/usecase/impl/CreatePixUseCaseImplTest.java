package com.pix.poc.application.usecase.impl;

import com.pix.poc.domain.entities.*;
import com.pix.poc.domain.exception.InvalidDocumentException;
import com.pix.poc.domain.exception.InvalidMaxValueCnpjException;
import com.pix.poc.domain.exception.InvalidMaxValueCpfException;
import com.pix.poc.domain.exception.InvalidPixValueException;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePixUseCaseImplTest {

    @Mock
    private PixRepository pixRepository;

    @Mock
    private AccountRepository accountRepository;


    @InjectMocks
    private CreatePixUseCaseImpl createPixUseCase;

    private CreatePixRequest createPixRequest;
    private Pix pix;
    private Pix pixCnpj;
    private Account account;
    private Account accountCnpj;
    private Document document;
    private Document documentCnpj;
    private PixValue pixValue;

    @BeforeEach
    void setUp() {
        createPixRequest = new CreatePixRequest(
                "12345678909",
                12345,
                "CORRENTE",
                1234,
                "João",
                "Silva",
                "EMAIL",
                "test@email.com"
        );

        document = new Document("12345678909");
        documentCnpj = new Document("04252011000110");
        pixValue = new PixValue("test@email.com", PixType.EMAIL);

        account = new Account.Builder()
                .document(document)
                .accountType(AccountType.CORRENTE)
                .accountNumber(new AccountNumber(12345))
                .agencyNumber(new AgencyNumber(1234))
                .build();

        accountCnpj = new Account.Builder()
                .document(documentCnpj)
                .accountType(AccountType.CORRENTE)
                .accountNumber(new AccountNumber(12345))
                .agencyNumber(new AgencyNumber(1234))
                .build();


        pix = new Pix.Builder()
                .account(account)
                .pixType(PixType.EMAIL)
                .pixValue(pixValue)
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        pixCnpj = new Pix.Builder()
                .account(accountCnpj)
                .pixType(PixType.EMAIL)
                .pixValue(pixValue)
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoDadosValidos() {
        when(pixRepository.existsByPixValue(anyString())).thenReturn(false);
        when(accountRepository.getAccountsByDocument(any(Document.class))).thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(anyList())).thenReturn(0L);
        when(pixRepository.save(any(Pix.class))).thenReturn(pix);

        SavePixResponse response = createPixUseCase.createPix(createPixRequest);

        assertNotNull(response);
        verify(pixRepository).existsByPixValue("test@email.com");
        verify(accountRepository).getAccountsByDocument(any(Document.class));
        verify(pixRepository).countPixByAccounts(anyList());
        verify(pixRepository).save(any(Pix.class));
    }

    @Test
    void createPix_DeveLancarExcecao_QuandoPixJaExiste() {
        when(pixRepository.existsByPixValue(anyString())).thenReturn(true);

        InvalidPixValueException exception = assertThrows(
                InvalidPixValueException.class,
                () -> createPixUseCase.createPix(createPixRequest)
        );

        assertEquals("Pix já cadastrado.", exception.getMessage());
        verify(pixRepository).existsByPixValue("test@email.com");
        verifyNoMoreInteractions(pixRepository, accountRepository);
    }

    @Test
    void createPix_DeveLancarExcecao_QuandoCPFTemMaisDe5Pix() {
        when(pixRepository.existsByPixValue(anyString())).thenReturn(false);
        when(accountRepository.getAccountsByDocument(any(Document.class))).thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(anyList())).thenReturn(6L);

        InvalidMaxValueCpfException exception = assertThrows(
                InvalidMaxValueCpfException.class,
                () -> createPixUseCase.createPix(createPixRequest)
        );

        assertEquals("Cliente possui mais de 5 pix cadastros para pessoa física", exception.getMessage());
        verify(pixRepository).existsByPixValue("test@email.com");
        verify(accountRepository).getAccountsByDocument(any(Document.class));
        verify(pixRepository).countPixByAccounts(anyList());
    }

    @Test
    void createPix_DeveLancarExcecao_QuandoCNPJTemMaisDe20Pix() {
        CreatePixRequest cnpjRequest = new CreatePixRequest(
                "04252011000110",
                12345,
                "CORRENTE",
                1234,
                "João",
                "Silva",
                "EMAIL",
                "test@email.com"
        );

        when(pixRepository.existsByPixValue(anyString())).thenReturn(false);
        when(accountRepository.getAccountsByDocument(any(Document.class))).thenReturn(Arrays.asList(accountCnpj));
        when(pixRepository.countPixByAccounts(anyList())).thenReturn(21L);

        InvalidMaxValueCnpjException exception = assertThrows(
                InvalidMaxValueCnpjException.class,
                () -> createPixUseCase.createPix(cnpjRequest)
        );

        assertEquals("Cliente possui mais de 20 pix cadastros para pessoa jurídica", exception.getMessage());
        verify(pixRepository).existsByPixValue("test@email.com");
        verify(accountRepository).getAccountsByDocument(any(Document.class));
        verify(pixRepository).countPixByAccounts(anyList());
    }

    @Test
    void createPix_DeveLancarExcecao_QuandoDocumentoNaoCorrespondeConta() {
        Account wrongAccount = new Account.Builder()
                .document(new Document("98765432100"))
                .accountType(AccountType.CORRENTE)
                .accountNumber(new AccountNumber(12345))
                .agencyNumber(new AgencyNumber(1234))
                .build();

        when(pixRepository.existsByPixValue(anyString())).thenReturn(false);
        when(accountRepository.getAccountsByDocument(any(Document.class))).thenReturn(Arrays.asList(wrongAccount));
        when(pixRepository.countPixByAccounts(anyList())).thenReturn(0L);

        InvalidDocumentException exception = assertThrows(
                InvalidDocumentException.class,
                () -> createPixUseCase.createPix(createPixRequest)
        );

        assertEquals("Documento nao possui relação com agencia e conta", exception.getMessage());
        verify(pixRepository).existsByPixValue("test@email.com");
        verify(accountRepository).getAccountsByDocument(any(Document.class));
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoCPFTem5PixOuMenos() {
        when(pixRepository.existsByPixValue(anyString())).thenReturn(false);
        when(accountRepository.getAccountsByDocument(any(Document.class))).thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(anyList())).thenReturn(5L);
        when(pixRepository.save(any(Pix.class))).thenReturn(pix);

        SavePixResponse response = createPixUseCase.createPix(createPixRequest);

        assertNotNull(response);
        verify(pixRepository).existsByPixValue("test@email.com");
        verify(accountRepository).getAccountsByDocument(any(Document.class));
        verify(pixRepository).countPixByAccounts(anyList());
        verify(pixRepository).save(any(Pix.class));
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoCNPJTem20PixOuMenos() {
        CreatePixRequest cnpjRequest = new CreatePixRequest(
                "04252011000110",
                12345,
                "CORRENTE",
                1234,
                "João",
                "Silva",
                "EMAIL",
                "test@email.com"
        );

        when(pixRepository.existsByPixValue(anyString())).thenReturn(false);
        when(accountRepository.getAccountsByDocument(any(Document.class))).thenReturn(Arrays.asList(accountCnpj));
        when(pixRepository.countPixByAccounts(anyList())).thenReturn(20L);
        when(pixRepository.save(any(Pix.class))).thenReturn(pix);

        SavePixResponse response = createPixUseCase.createPix(cnpjRequest);

        assertNotNull(response);
        verify(pixRepository).existsByPixValue("test@email.com");
        verify(accountRepository).getAccountsByDocument(any(Document.class));
        verify(pixRepository).countPixByAccounts(anyList());
        verify(pixRepository).save(any(Pix.class));
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoTipoPixDiferente() {
        CreatePixRequest phoneRequest = new CreatePixRequest(
                "12345678909",
                12345,
                "CORRENTE",
                1234,
                "João",
                "Silva",
                "CELULAR",
                "+5511999999999"
        );

        when(pixRepository.existsByPixValue(anyString())).thenReturn(false);
        when(accountRepository.getAccountsByDocument(any(Document.class))).thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(anyList())).thenReturn(0L);
        when(pixRepository.save(any(Pix.class))).thenReturn(pix);

        SavePixResponse response = createPixUseCase.createPix(phoneRequest);

        assertNotNull(response);
        verify(pixRepository).existsByPixValue("+5511999999999");
        verify(accountRepository).getAccountsByDocument(any(Document.class));
        verify(pixRepository).countPixByAccounts(anyList());
        verify(pixRepository).save(any(Pix.class));
    }

    @Test
    void createPix_DeveCriarPixComSucesso_QuandoTipoContaDiferente() {
        CreatePixRequest poupancaRequest = new CreatePixRequest(
                "12345678909",
                12345,
                "POUPANCA",
                1234,
                "João",
                "Silva",
                "EMAIL",
                "test@email.com"
        );

        when(pixRepository.existsByPixValue(anyString())).thenReturn(false);
        when(accountRepository.getAccountsByDocument(any(Document.class))).thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(anyList())).thenReturn(0L);
        when(pixRepository.save(any(Pix.class))).thenReturn(pix);

        SavePixResponse response = createPixUseCase.createPix(poupancaRequest);

        assertNotNull(response);
        verify(pixRepository).existsByPixValue("test@email.com");
        verify(accountRepository).getAccountsByDocument(any(Document.class));
        verify(pixRepository).countPixByAccounts(anyList());
        verify(pixRepository).save(any(Pix.class));
    }
}
