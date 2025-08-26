package com.pix.poc.application.usecase;

import com.pix.poc.application.usecase.impl.CreatePixUseCaseImpl;
import com.pix.poc.application.usecase.impl.GetPixUseCaseImpl;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;

import com.pix.poc.domain.entities.DocumentType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.repository.AccountRepository;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UseCaseIntegrationTest {

    @Mock
    private PixRepository pixRepository;

    @Mock
    private AccountRepository accountRepository;

    private CreatePixUseCaseImpl createPixUseCase;
    private GetPixUseCaseImpl getPixUseCase;

    @BeforeEach
    void setUp() {
        createPixUseCase = new CreatePixUseCaseImpl(pixRepository, accountRepository);
        getPixUseCase = new GetPixUseCaseImpl(pixRepository);
    }

    @Test
    void fluxoCompleto_DeveCriarERecuperarPix_QuandoOperacoesSaoExecutadasEmSequencia() {
        // Arrange - Criar PIX
        Account account = createTestAccount();
        Pix pix = createTestPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(2L);
        
        // Mock do repository para retornar um UUID específico
        UUID pixId = UUID.randomUUID();
        when(pixRepository.save(any(Pix.class)))
                .thenReturn(pixId);

        // Act - Criar PIX
        String createdPixId = createPixUseCase.createPix(pix);

        // Assert - Verificar se PIX foi criado
        assertEquals(pixId.toString(), createdPixId);
        verify(pixRepository).save(pix);

        // Arrange - Buscar PIX criado
        // Criar um PIX com o ID retornado para simular o que seria retornado pelo repository
        Pix pixComId = createTestPixWithId(account, pixId.toString());
        
        PixFilterRequest filterRequest = new PixFilterRequest(
                createdPixId, null, null, null, null, null, null
        );
        
        when(pixRepository.get(
                eq(createdPixId), eq(null), eq(createdPixId), eq(null), eq(null), 
                eq(null), eq(null), eq(null)
        )).thenReturn(Arrays.asList(pixComId));

        // Act - Buscar PIX
        List<GetPixResponse> foundPix = getPixUseCase.getPix(filterRequest);

        // Assert - Verificar se PIX foi encontrado
        assertNotNull(foundPix);
        assertEquals(1, foundPix.size());
        assertEquals(createdPixId, foundPix.get(0).id());
        assertEquals("EMAIL", foundPix.get(0).pixType());
        assertEquals(1234, foundPix.get(0).agencyNumber());
        assertEquals(12345, foundPix.get(0).accountNumber());
        assertEquals("João Silva", foundPix.get(0).name());
    }

    @Test
    void fluxoCompleto_DeveCriarERecuperarMultiplosPix_QuandoOperacoesSaoExecutadasEmSequencia() {
        // Arrange - Criar múltiplos PIX
        Account account = createTestAccount();
        Pix pix1 = createTestPix(account);
        Pix pix2 = createTestPix(account);
        
        when(accountRepository.getAccountsByDocument(any(Document.class)))
                .thenReturn(Arrays.asList(account));
        when(pixRepository.countPixByAccounts(any()))
                .thenReturn(2L);
        
        // Mock do repository para retornar UUIDs específicos
        UUID pixId1 = UUID.randomUUID();
        UUID pixId2 = UUID.randomUUID();
        when(pixRepository.save(pix1))
                .thenReturn(pixId1);
        when(pixRepository.save(pix2))
                .thenReturn(pixId2);

        // Act - Criar PIXs
        String createdPixId1 = createPixUseCase.createPix(pix1);
        String createdPixId2 = createPixUseCase.createPix(pix2);

        // Assert - Verificar se PIXs foram criados
        assertEquals(pixId1.toString(), createdPixId1);
        assertEquals(pixId2.toString(), createdPixId2);
        verify(pixRepository, times(2)).save(any(Pix.class));

        // Arrange - Buscar todos os PIXs da conta
        // Criar PIXs com IDs para simular o que seria retornado pelo repository
        Pix pix1ComId = createTestPixWithId(account, pixId1.toString());
        Pix pix2ComId = createTestPixWithId(account, pixId2.toString());
        
        PixFilterRequest filterRequest = new PixFilterRequest(
                null, null, 1234, 12345, null, null, null
        );
        
        when(pixRepository.get(
                eq(null), eq(null), eq(null), eq(1234), eq(12345), 
                eq(null), eq(null), eq(null)
        )).thenReturn(Arrays.asList(pix1ComId, pix2ComId));

        // Act - Buscar PIXs
        List<GetPixResponse> foundPix = getPixUseCase.getPix(filterRequest);

        // Assert - Verificar se PIXs foram encontrados
        assertNotNull(foundPix);
        assertEquals(2, foundPix.size());
        assertTrue(foundPix.stream().anyMatch(p -> p.id().equals(createdPixId1)));
        assertTrue(foundPix.stream().anyMatch(p -> p.id().equals(createdPixId2)));
    }

    private Account createTestAccount() {
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

    private Pix createTestPix(Account account) {
        return new Pix.Builder()
                .account(account)
                .pixType(PixType.EMAIL)
                .pixValue(new PixValue("joao@email.com", PixType.EMAIL))
                .inclusionDate(LocalDate.now())
                .build();
    }

    private Pix createTestPixWithId(Account account, String id) {
        return new Pix.Builder()
                .uniqueID(id)
                .account(account)
                .pixType(PixType.EMAIL)
                .pixValue(new PixValue("joao@email.com", PixType.EMAIL))
                .inclusionDate(LocalDate.now())
                .build();
    }
} 