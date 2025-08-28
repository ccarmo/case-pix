package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.ValidatePixUseCase;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.exception.PixNotFoundException;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.PixValue;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.interactors.web.dto.request.PixFilterRequest;
import com.pix.poc.interactors.web.dto.response.GetPixResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class GetPixUseCaseImplTest {

    private PixRepository pixRepository;
    private ValidatePixUseCase validatePixUseCase;
    private GetPixUseCaseImpl getPixUseCase;

    @BeforeEach
    void setup() {
        pixRepository = mock(PixRepository.class);
        validatePixUseCase = mock(ValidatePixUseCase.class);
        getPixUseCase = new GetPixUseCaseImpl(pixRepository, validatePixUseCase);
    }

    @Test
    void getPix_DeveRetornarListaQuandoPixExistir() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                "123",
                "CPF",
                1234,
                567890,
                "Carlos",
                LocalDate.now(),
                null
        );

        Account account = new Account.Builder()
                .accountNumber(new AccountNumber(567890))
                .agencyNumber(new AgencyNumber(1234))
                .name("Carlos")
                .lastName("Silva")
                .accountType(AccountType.POUPANCA)
                .document(new Document("12345678900"))
                .build();

        Pix pix = new Pix.Builder()
                .uniqueID("123")
                .pixType(PixType.CPF)
                .pixValue(new PixValue("12345678900", PixType.CELULAR))
                .account(account)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        when(pixRepository.get(
                anyString(), anyString(), anyString(), anyInt(), anyInt(), anyString(), any(), any()
        )).thenReturn(List.of(pix));

        // Act
        List<GetPixResponse> response = getPixUseCase.getPix(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.isEmpty());
        verify(validatePixUseCase, times(1)).validatePix("123");
        verify(pixRepository, times(1)).get(
                anyString(), anyString(), anyString(), anyInt(), anyInt(), anyString(), any(), any()
        );
    }

    @Test
    void getPix_DeveLancarPixNotFoundException_QuandoListaVazia() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                "123",
                "CPF",
                1234,
                567890,
                "Carlos",
                LocalDate.now(),
                null
        );

        when(pixRepository.get(
                anyString(), anyString(), anyString(), anyInt(), anyInt(), anyString(), any(), any()
        )).thenReturn(List.of()); // lista vazia

        // Act & Assert
        PixNotFoundException exception = assertThrows(
                PixNotFoundException.class,
                () -> getPixUseCase.getPix(request)
        );

        assertEquals("Nao há valor de pix para os parametro(s) pesquisado(s)", exception.getMessage());
        verify(validatePixUseCase, times(1)).validatePix("123");
    }

    @Test
    void getPix_DeveChamarValidatePixUseCase() {
        // Arrange
        PixFilterRequest request = new PixFilterRequest(
                "999",
                "EMAIL",
                1111,
                222222,
                "Maria",
                LocalDate.now(),
                null
        );

        Account account = new Account.Builder()
                .accountNumber(new AccountNumber(222222))
                .agencyNumber(new AgencyNumber(1111))
                .name("Maria")
                .lastName("Souza")
                .accountType(AccountType.POUPANCA)
                .document(new Document("98765432100"))
                .build();

        Pix pix = new Pix.Builder()
                .uniqueID("999")
                .pixType(PixType.EMAIL)
                .pixValue(new PixValue("maria@email.com", PixType.EMAIL))
                .account(account)
                .build();

        when(pixRepository.get(
                anyString(), anyString(), anyString(), anyInt(), anyInt(), anyString(), any(), any()
        )).thenReturn(List.of(pix));

        // Act
        getPixUseCase.getPix(request);

        // Assert
        verify(validatePixUseCase, times(1)).validatePix("999");
    }
}
