package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.CreatePixUseCase;
import com.pix.poc.application.usecase.ValidateAccountPixUseCase;
import com.pix.poc.domain.entities.*;

import com.pix.poc.domain.exception.InvalidDocumentException;
import com.pix.poc.domain.exception.InvalidMaxValueCnpjException;
import com.pix.poc.domain.exception.InvalidMaxValueCpfException;
import com.pix.poc.domain.exception.InvalidPixValueException;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.*;
import com.pix.poc.application.web.dto.request.CreatePixRequest;
import com.pix.poc.application.web.dto.response.SavePixResponse;
import org.springframework.stereotype.Service;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CreatePixUseCaseImpl implements CreatePixUseCase {

    PixRepository pixRepository;
    AccountRepository accountRepository;

    public CreatePixUseCaseImpl(PixRepository pixRepository, AccountRepository accountRepository, ValidateAccountPixUseCase validateAccountPixUseCase) {
        this.pixRepository = pixRepository;
        this.accountRepository = accountRepository;

    }

    @Override
    public SavePixResponse createPix(CreatePixRequest createPixRequest) {

        Name name = new Name(createPixRequest.nameClient());
        LastName lastName = new LastName(createPixRequest.lastNameClient());
        Document document = new Document(createPixRequest.documentNumber());
        AccountType accountType  = AccountType.valueOfOrThrow(createPixRequest.accountType());
        AccountNumber accountNumber = new AccountNumber(createPixRequest.accountNumber());
        AgencyNumber agencyNumber = new AgencyNumber(createPixRequest.agencyNumber());

        PixType pixTypeDomain = PixType.fromString(createPixRequest.pixType());
        PixValue pixValueDomain = new PixValue(createPixRequest.pixValue(), pixTypeDomain);

        if(pixRepository.existsByPixValue(pixValueDomain.getValue())) {
            throw new InvalidPixValueException("Pix já cadastrado.");
        }


        List<Account> accountList = accountRepository.getAccountsByDocument(document);

        Long count = pixRepository.countPixByAccounts(accountList);


        if (!accountList.isEmpty()) {
            Account account = accountList.getFirst();
            if (!account.getDocument().getValue().equals(createPixRequest.documentNumber())) {
                throw new InvalidDocumentException("Documento nao possui relação com agencia e conta");
            }
        } else {
            Optional<Account> existingAccount = accountRepository.findByAccountNumberAndAgencyNumber(
                    new AccountNumber(createPixRequest.accountNumber()),
                    new AgencyNumber(createPixRequest.agencyNumber())

            );
            if (existingAccount.isPresent() && !existingAccount.get().getDocument().getValue().equals(createPixRequest.documentNumber())) {
                throw new InvalidDocumentException("Agência e conta já estão associadas a outro documento");
            }
        }

        if (document.getType().equals(DocumentType.CPF) && count > 5) {
            throw new InvalidMaxValueCpfException("Cliente possui mais de 5 pix cadastros para pessoa física");
        }

        if (document.getType().equals(DocumentType.CNPJ) && count > 20) {
            throw new InvalidMaxValueCnpjException("Cliente possui mais de 20 pix cadastros para pessoa jurídica");
        }



        Account account = new Account.Builder()
                .document(document)
                .name(name.value())
                .lastName(lastName.value())
                .accountType(accountType)
                .accountNumber(accountNumber)
                .agencyNumber(agencyNumber)
                .build();



        Pix pix = new Pix.Builder()
                .account(account)
                .pixType(pixTypeDomain)
                .pixValue(pixValueDomain)
                .active(true)
                .inclusionDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();


        Pix pixSaved = pixRepository.save(pix);

        return SavePixResponse.from(pixSaved.getUniqueID().value());

    }
}
