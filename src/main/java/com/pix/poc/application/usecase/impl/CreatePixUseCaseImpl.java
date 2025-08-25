package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.CreatePixUseCase;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.DocumentType;
import com.pix.poc.domain.entities.Pix;

import com.pix.poc.domain.exception.InvalidMaxValueCnpjException;
import com.pix.poc.domain.exception.InvalidMaxValueCpfException;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.repository.PixRepository;
import org.springframework.stereotype.Service;

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
    public void createPix(Pix pix) {
        List<Account> accountList = accountRepository.getAccountsByDocument(pix.getAccount().getDocument());
        Long count = pixRepository.countPixByAccounts(accountList);

        if (pix.getAccount().getDocument().getType().equals(DocumentType.CPF) && count > 5) {
            throw new InvalidMaxValueCpfException("Cliente possui mais de 5 pix cadastros para pessoa física");
        }

        if (pix.getAccount().getDocument().getType().equals(DocumentType.CNPJ) && count > 5) {
            throw new InvalidMaxValueCnpjException("Cliente possui mais de 20 pix cadastros para pessoa jurídica");
        }


        pixRepository.save(pix);
    }
}
