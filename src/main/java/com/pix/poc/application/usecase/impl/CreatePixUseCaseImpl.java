package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.CreatePixUseCase;
import com.pix.poc.domain.entities.*;

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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@Service
public class CreatePixUseCaseImpl implements CreatePixUseCase {

    PixRepository pixRepository;
    AccountRepository accountRepository;

    public CreatePixUseCaseImpl(PixRepository pixRepository, AccountRepository accountRepository) {
        this.pixRepository = pixRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public SavePixResponse createPix(CreatePixRequest createPixRequest) {

        PixType pixTypeDomain = PixType.fromString(createPixRequest.pixType());
        PixValue pixValueDomain = new PixValue(createPixRequest.pixValue(), pixTypeDomain);

        if(pixRepository.existsByPixValue(pixValueDomain.getValue())) {
            throw new InvalidPixValueException("Pix já cadastrado.");
        }

        Document document = new Document(createPixRequest.documentNumber());
        List<Account> accountList = accountRepository.getAccountsByDocument(document);
        Long count = pixRepository.countPixByAccounts(accountList);

        if (document.getType().equals(DocumentType.CPF) && count > 5) {
            throw new InvalidMaxValueCpfException("Cliente possui mais de 5 pix cadastros para pessoa física");
        }

        if (document.getType().equals(DocumentType.CNPJ) && count > 20) {
            throw new InvalidMaxValueCnpjException("Cliente possui mais de 20 pix cadastros para pessoa jurídica");
        }

        Account account = new Account.Builder()
                .document(document)
                .accountType(AccountType.valueOfOrThrow(createPixRequest.accountType()))
                .accountNumber(new AccountNumber(createPixRequest.accountNumber()))
                .agencyNumber(new AgencyNumber(createPixRequest.agencyNumber()))
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
