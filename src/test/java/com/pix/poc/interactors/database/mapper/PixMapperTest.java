package com.pix.poc.interactors.database.mapper;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.vo.*;
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

        
        document = new Document("12345678909");
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
                .uniqueID(new PixId("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
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
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", String.valueOf(result.getId()));
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
        assertEquals("12345678909", accountModel.getDocumentNumber());
        
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
                .uniqueID(new PixId("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
                .account(account)
                .pixType(PixType.CPF)
                .pixValue(new PixValue("12345678909", PixType.CPF))
                .active(false)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .inactivationDate(inactivationDate)
                .build();

        // Act
        PixModel result = pixMapper.toModel(inactivePix);

        // Assert
        assertNotNull(result);
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", String.valueOf(result.getId()));
        assertEquals("CPF", result.getPixType());
        assertEquals("12345678909", result.getPixValue());
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
                .uniqueID(new PixId("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
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
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getId());
        
        AccountModel accountModel = result.getAccount();
        assertNotNull(accountModel);
        assertNull(accountModel.getAccountType());
    }

    @Test
    void toModel_DeveConverterPixComDiferentesTipos_QuandoPixTypeDiferente() {
        // Arrange
        Pix pixCelular = new Pix.Builder()
                .uniqueID(new PixId("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
                .account(account)
                .pixType(PixType.CELULAR)
                .pixValue(new PixValue("+1155987654321", PixType.CELULAR))
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        // Act
        PixModel result = pixMapper.toModel(pixCelular);

        // Assert
        assertNotNull(result);
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getId());
        assertEquals("CELULAR", result.getPixType());
        assertEquals("+1155987654321", result.getPixValue());
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
                "12345678909"
        );
        
        Instant inclusionDate = Instant.now();
        PixModel pixModel = new PixModel(
                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
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
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getUniqueID().value());
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
        assertEquals("12345678909", resultAccount.getDocument().getValue());
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
                "12345678909"
        );
        
        Instant inclusionDate = Instant.now();
        Instant inactivationDate = Instant.now();
        PixModel pixModel = new PixModel(
                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                "CPF",
                "12345678909",
                accountModel,
                inclusionDate,
                inactivationDate,
                false
        );

        // Act
        Pix result = pixMapper.toDomain(pixModel);

        // Assert
        assertNotNull(result);
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getUniqueID().value());
        assertEquals(PixType.CPF, result.getPixType());
        assertEquals("12345678909", result.getPixValue().getValue());
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
                "12345678909"
        );
        
        Instant inclusionDate = Instant.now();
        PixModel pixModel = new PixModel(
                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                "ALEATORIO",
                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                accountModel,
                inclusionDate,
                null,
                true
        );


        Pix result = pixMapper.toDomain(pixModel);


        assertNotNull(result);
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getUniqueID().value());
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
                "12345678909"
        );
        
        Instant inclusionDate = Instant.now();
        PixModel pixModel = new PixModel(
                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                "CNPJ",
                "04252011000110",
                accountModel,
                inclusionDate,
                null,
                true
        );

        // Act
        Pix result = pixMapper.toDomain(pixModel);

        // Assert
        assertNotNull(result);
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getUniqueID().value());
        assertEquals(PixType.CNPJ, result.getPixType());
        assertEquals("04252011000110", result.getPixValue().getValue());
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
                .uniqueID(new PixId("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
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
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getId());
        
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
                "12345678909"
        );
        
        Instant inclusionDate = Instant.now();
        PixModel pixModel = new PixModel(
                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
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
        assertEquals("f47ac10b-58cc-4372-a567-0e02b2c3d479", result.getUniqueID().value());
        
        Account resultAccount = result.getAccount();
        assertNotNull(resultAccount);
        assertEquals(AccountType.POUPANCA, resultAccount.getAccountType());
    }
}
