package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.ValidateAccountPixUseCase;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.exception.AccountNotFoundException;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidateAccountPixUseCaseImpl implements ValidateAccountPixUseCase {

    AccountRepository accountRepository;

    ValidateAccountPixUseCaseImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account validateAccount(AccountNumber accountNumber, AgencyNumber agencyNumber) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumberAndAgencyNumber(accountNumber, agencyNumber);
        if(accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Conta e Agência enviada para alteração não existe.");
        }
        return accountOptional.get();
    }


}
