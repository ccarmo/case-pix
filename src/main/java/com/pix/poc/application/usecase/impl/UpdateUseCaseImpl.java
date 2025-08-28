package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.UpdateUseCase;
import com.pix.poc.application.usecase.ValidateAccountPixUseCase;
import com.pix.poc.application.usecase.ValidatePixUseCase;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.*;
import com.pix.poc.application.web.dto.request.UpdatePixRequest;
import com.pix.poc.application.web.dto.response.UpdatePixResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UpdateUseCaseImpl implements UpdateUseCase {

    ValidatePixUseCase validatePixUseCase;
    ValidateAccountPixUseCase validateAccountPixUseCase;
    PixRepository pixRepository;
    AccountRepository accountRepository;

    public UpdateUseCaseImpl(ValidatePixUseCase validatePixUseCase, ValidateAccountPixUseCase validateAccountPixUseCase, PixRepository pixRepository) {
        this.validatePixUseCase = validatePixUseCase;
        this.validateAccountPixUseCase = validateAccountPixUseCase;
        this.pixRepository = pixRepository;
    }

    @Override
    @Transactional
    public UpdatePixResponse changePix(UpdatePixRequest updatePixRequest) {

        Name name = new Name(updatePixRequest.nameClient());
        LastName lastName = new LastName(updatePixRequest.lastNameClient());
        AccountType accountType  = AccountType.valueOfOrThrow(updatePixRequest.accountType());

        Pix pix = validatePixUseCase.validatePix(updatePixRequest.id());
        AccountNumber accountNumber = new AccountNumber(updatePixRequest.accountNumber());
        AgencyNumber agencyNumber = new AgencyNumber(updatePixRequest.agencyNumber());
        Account account = validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        Account newAccount = new Account(
                account.getDocument(),
                name.value(),
                lastName.value(),
                new AgencyNumber(updatePixRequest.agencyNumber()),
                new AccountNumber(updatePixRequest.accountNumber()),
                AccountType.valueOf(accountType.name())
        );

        Pix newPix =  pixRepository.updatePix(newAccount, pix);

        return UpdatePixResponse.fromUpdatePixRequest(newPix.getUniqueID().value(), account);

    }
}
