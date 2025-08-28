package com.pix.poc.interactors.database.mapper;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.domain.vo.PixValue;
import com.pix.poc.interactors.database.model.AccountId;
import com.pix.poc.interactors.database.model.AccountModel;
import com.pix.poc.interactors.database.model.PixModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PixMapperTest {


    @InjectMocks
    private PixMapper pixMapper;




    private Pix pix;
    private Account account;
    private Document document;
    private AccountNumber accountNumber;
    private AgencyNumber agencyNumber;
    private PixValue pixValue;

    @BeforeEach
    void setUp() {

        
        document = new Document("12345678901");
        accountNumber = new AccountNumber(12345);
        agencyNumber = new AgencyNumber(1234);
        pixValue = new PixValue("joao@email.com", PixType.EMAIL);
        
        account = new Account.Builder()
                .document(document)
                .accountType(AccountType.CORRENTE)
                .accountNumber(accountNumber)
                .agencyNumber(agencyNumber)
                .name("João")
                .lastName("Silva")
                .build();
        
        pix = new Pix.Builder()
                .uniqueID("pix-123")
                .account(account)
                .pixType(PixType.EMAIL)
                .pixValue(pixValue)
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();
    }

    @Test
    void toModel_DeveConverterPixParaPixModel_QuandoPixValido() {
        // Act
        PixModel result = pixMapper.toModel(pix);

        // Assert
        assertNotNull(result);
        assertEquals("pix-123", result.getId());
        assertEquals("EMAIL", result.getPixType());
        assertEquals("joao@email.com", result.getPixValue());
        assertTrue(result.getActive());
        assertNotNull(result.getInclusionDate());
        assertNull(result.getInactivationDate());
        
        // Verificar AccountModel
        AccountModel accountModel = result.getAccount();
        assertNotNull(accountModel);
        assertEquals("João", accountModel.getName());
        assertEquals("Silva", accountModel.getLastName());
        assertEquals("CORRENTE", accountModel.getAccountType());
        assertEquals("12345678901", accountModel.getDocumentNumber());
        
        // Verificar AccountId
        AccountId accountId = accountModel.getId();
        assertNotNull(accountId);
        assertEquals(12345, accountId.getAccountNumber());
        assertEquals(1234, accountId.getAgencyNumber());
    }

    @Test
    void toModel_DeveConverterPixComInactivationDate_QuandoPixInativo() {
        // Arrange
        ZonedDateTime inactivationDate = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        Pix inactivePix = new Pix.Builder()
                .uniqueID("pix-456")
                .account(account)
                .pixType(PixType.CPF)
                .pixValue(new PixValue("12345678901", PixType.CPF))
                .active(false)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .inactivationDate(inactivationDate)
                .build();

        // Act
        PixModel result = pixMapper.toModel(inactivePix);

        // Assert
        assertNotNull(result);
        assertEquals("pix-456", result.getId());
        assertEquals("CPF", result.getPixType());
        assertEquals("12345678901", result.getPixValue());
        assertFalse(result.getActive());
        assertNotNull(result.getInclusionDate());
        assertNotNull(result.getInactivationDate());
    }

    @Test
    void toModel_DeveConverterPixComAccountTypeNulo_QuandoAccountTypeNulo() {
        // Arrange
        Account accountWithoutType = new Account.Builder()
                .document(document)
                .accountType(null)
                .accountNumber(accountNumber)
                .agencyNumber(agencyNumber)
                .name("João")
                .lastName("Silva")
                .build();
        
        Pix pixWithoutAccountType = new Pix.Builder()
                .uniqueID("pix-789")
                .account(accountWithoutType)
                .pixType(PixType.EMAIL)
                .pixValue(pixValue)
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        // Act
        PixModel result = pixMapper.toModel(pixWithoutAccountType);

        // Assert
        assertNotNull(result);
        assertEquals("pix-789", result.getId());
        
        AccountModel accountModel = result.getAccount();
        assertNotNull(accountModel);
        assertNull(accountModel.getAccountType());
    }

    @Test
    void toModel_DeveConverterPixComDiferentesTipos_QuandoPixTypeDiferente() {
        // Arrange
        Pix pixCelular = new Pix.Builder()
                .uniqueID("pix-celular")
                .account(account)
                .pixType(PixType.CELULAR)
                .pixValue(new PixValue("11987654321", PixType.CELULAR))
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        // Act
        PixModel result = pixMapper.toModel(pixCelular);

        // Assert
        assertNotNull(result);
        assertEquals("pix-celular", result.getId());
        assertEquals("CELULAR", result.getPixType());
        assertEquals("11987654321", result.getPixValue());
    }

    @Test
    void toDomain_DeveConverterPixModelParaPix_QuandoPixModelValido() {
        // Arrange
        AccountId accountId = new AccountId(12345, 1234);
        AccountModel accountModel = new AccountModel(
                accountId,
                "CORRENTE",
                "João",
                "Silva",
                "12345678901"
        );
        
        Instant inclusionDate = Instant.now();
        PixModel pixModel = new PixModel(
                "pix-123",
                "EMAIL",
                "joao@email.com",
                accountModel,
                inclusionDate,
                null,
                true
        );

        // Act
        Pix result = pixMapper.toDomain(pixModel);

        // Assert
        assertNotNull(result);
        assertEquals("pix-123", result.getUniqueID());
        assertEquals(PixType.EMAIL, result.getPixType());
        assertEquals("joao@email.com", result.getPixValue().getValue());
        assertTrue(result.isActive());
        assertNotNull(result.getInclusionDate());
        assertNull(result.getInactivationDate());
        
        // Verificar Account
        Account resultAccount = result.getAccount();
        assertNotNull(resultAccount);
        assertEquals("João", resultAccount.getName());
        assertEquals("Silva", resultAccount.getLastName());
        assertEquals(AccountType.CORRENTE, resultAccount.getAccountType());
        assertEquals("12345678901", resultAccount.getDocument().getValue());
        assertEquals(12345, resultAccount.getAccountNumber().getValue());
        assertEquals(1234, resultAccount.getAgencyNumber().getValue());
    }

    @Test
    void toDomain_DeveConverterPixModelComInactivationDate_QuandoPixModelInativo() {
        // Arrange
        AccountId accountId = new AccountId(12345, 1234);
        AccountModel accountModel = new AccountModel(
                accountId,
                "POUPANCA",
                "Maria",
                "Santos",
                "98765432100"
        );
        
        Instant inclusionDate = Instant.now();
        Instant inactivationDate = Instant.now();
        PixModel pixModel = new PixModel(
                "pix-456",
                "CPF",
                "98765432100",
                accountModel,
                inclusionDate,
                inactivationDate,
                false
        );

        // Act
        Pix result = pixMapper.toDomain(pixModel);

        // Assert
        assertNotNull(result);
        assertEquals("pix-456", result.getUniqueID());
        assertEquals(PixType.CPF, result.getPixType());
        assertEquals("98765432100", result.getPixValue().getValue());
        assertFalse(result.isActive());
        assertNotNull(result.getInclusionDate());
        assertNotNull(result.getInactivationDate());
        
        // Verificar Account
        Account resultAccount = result.getAccount();
        assertNotNull(resultAccount);
        assertEquals("Maria", resultAccount.getName());
        assertEquals("Santos", resultAccount.getLastName());
        assertEquals(AccountType.POUPANCA, resultAccount.getAccountType());
    }

    @Test
    void toDomain_DeveConverterPixModelComAccountTypeNulo_QuandoAccountTypeNulo() {
        // Arrange
        AccountId accountId = new AccountId(12345, 1234);
        AccountModel accountModel = new AccountModel(
                accountId,
                null,
                "João",
                "Silva",
                "12345678901"
        );
        
        Instant inclusionDate = Instant.now();
        PixModel pixModel = new PixModel(
                "pix-789",
                "ALEATORIO",
                "random123",
                accountModel,
                inclusionDate,
                null,
                true
        );

        // Act
        Pix result = pixMapper.toDomain(pixModel);

        // Assert
        assertNotNull(result);
        assertEquals("pix-789", result.getUniqueID());
        assertEquals(PixType.ALEATORIO, result.getPixType());
        
        // Verificar Account
        Account resultAccount = result.getAccount();
        assertNotNull(resultAccount);
        assertNull(resultAccount.getAccountType());
    }

    @Test
    void toDomain_DeveConverterPixModelComDiferentesTipos_QuandoPixTypeDiferente() {
        // Arrange
        AccountId accountId = new AccountId(12345, 1234);
        AccountModel accountModel = new AccountModel(
                accountId,
                "CORRENTE",
                "João",
                "Silva",
                "12345678901"
        );
        
        Instant inclusionDate = Instant.now();
        PixModel pixModel = new PixModel(
                "pix-cnpj",
                "CNPJ",
                "12345678000199",
                accountModel,
                inclusionDate,
                null,
                true
        );

        // Act
        Pix result = pixMapper.toDomain(pixModel);

        // Assert
        assertNotNull(result);
        assertEquals("pix-cnpj", result.getUniqueID());
        assertEquals(PixType.CNPJ, result.getPixType());
        assertEquals("12345678000199", result.getPixValue().getValue());
    }


    @Test
    void toModel_DeveConverterPixComDiferentesAccountTypes_QuandoAccountTypesDiferentes() {
        // Arrange
        Account accountPoupanca = new Account.Builder()
                .document(document)
                .accountType(AccountType.POUPANCA)
                .accountNumber(accountNumber)
                .agencyNumber(agencyNumber)
                .name("João")
                .lastName("Silva")
                .build();
        
        Pix pixPoupanca = new Pix.Builder()
                .uniqueID("pix-poupanca")
                .account(accountPoupanca)
                .pixType(PixType.EMAIL)
                .pixValue(pixValue)
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        // Act
        PixModel result = pixMapper.toModel(pixPoupanca);

        // Assert
        assertNotNull(result);
        assertEquals("pix-poupanca", result.getId());
        
        AccountModel accountModel = result.getAccount();
        assertNotNull(accountModel);
        assertEquals("POUPANCA", accountModel.getAccountType());
    }

    @Test
    void toDomain_DeveConverterPixModelComDiferentesAccountTypes_QuandoAccountTypesDiferentes() {
        // Arrange
        AccountId accountId = new AccountId(12345, 1234);
        AccountModel accountModel = new AccountModel(
                accountId,
                "POUPANCA",
                "João",
                "Silva",
                "12345678901"
        );
        
        Instant inclusionDate = Instant.now();
        PixModel pixModel = new PixModel(
                "pix-poupanca",
                "EMAIL",
                "joao@email.com",
                accountModel,
                inclusionDate,
                null,
                true
        );

        // Act
        Pix result = pixMapper.toDomain(pixModel);

        // Assert
        assertNotNull(result);
        assertEquals("pix-poupanca", result.getUniqueID());
        
        Account resultAccount = result.getAccount();
        assertNotNull(resultAccount);
        assertEquals(AccountType.POUPANCA, resultAccount.getAccountType());
    }
}
