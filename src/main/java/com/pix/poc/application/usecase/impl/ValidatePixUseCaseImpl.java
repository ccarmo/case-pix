package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.ValidatePixUseCase;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.exception.PixInactiveExpcetion;
import com.pix.poc.domain.exception.PixNotFoundException;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.repository.PixRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidatePixUseCaseImpl implements ValidatePixUseCase {


    PixRepository pixRepository;
    AccountRepository accountRepository;

    public ValidatePixUseCaseImpl(PixRepository pixRepository, AccountRepository accountRepository) {
        this.pixRepository = pixRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Pix validatePix(String id) {

        Optional<Pix> pix = pixRepository.findById(id);

        if(pix.isEmpty()) {
            throw new PixNotFoundException("Nao há valor de pix para os parametro(s) pesquisado(s)");
        }

        if(pix.get().isActive().equals(false)) {
            throw new PixInactiveExpcetion("Pix desativado. Não é possível realizar alterações");
        }

        return pix.get();
    }
}
