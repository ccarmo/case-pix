package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.UpdateUseCase;
import com.pix.poc.application.usecase.ValidateAccountPixUseCase;
import com.pix.poc.application.usecase.ValidatePixUseCase;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.exception.PixInactiveExpcetion;
import com.pix.poc.domain.exception.PixNotFoundException;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.interactors.web.dto.request.UpdatePixRequest;
import com.pix.poc.interactors.web.dto.response.UpdatePixResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        Pix pix = validatePixUseCase.validatePix(updatePixRequest.id());
        AccountNumber accountNumber = new AccountNumber(updatePixRequest.accountNumber());
        AgencyNumber agencyNumber = new AgencyNumber(updatePixRequest.agencyNumber());
        Account account = validateAccountPixUseCase.validateAccount(accountNumber, agencyNumber);

        Account newAccount = new Account(
                account.getDocument(),
                updatePixRequest.nameClient(),
                updatePixRequest.lastNameClient(),
                new AgencyNumber(updatePixRequest.agencyNumber()),
                new AccountNumber(updatePixRequest.accountNumber()),
                AccountType.valueOf(updatePixRequest.accountType())
        );

        Pix newPix =  pixRepository.updatePix(newAccount, pix);

        return UpdatePixResponse.fromUpdatePixRequest(newPix.getUniqueID().value(), account);

    }
}
