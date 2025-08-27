package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.UpdateUseCase;
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

    PixRepository pixRepository;
    AccountRepository accountRepository;

    public UpdateUseCaseImpl(PixRepository pixRepository, AccountRepository accountRepository) {
        this.pixRepository = pixRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UpdatePixResponse changePix(UpdatePixRequest updatePixRequest) {
        Optional<Pix> pixOptional = pixRepository.findById(updatePixRequest.id());

        if(pixOptional.isEmpty()) {
            throw new PixNotFoundException("Nao há valor de pix para os parametro(s) pesquisado(s)");
        }

        if(pixOptional.get().isActive().equals(false)) {
            throw new PixInactiveExpcetion("Pix desativado. Não é possível realizar alterações");
        }

        Account accountPixId = pixOptional.get().getAccount();


        accountPixId.setName(updatePixRequest.nameClient());
        accountPixId.setLastName(updatePixRequest.lastNameClient());
        accountPixId.setAccountType(AccountType.valueOf(updatePixRequest.accountType()));

        pixOptional.get().setAccount(accountPixId);
        pixRepository.save(pixOptional.get());
        //accountRepository.save(accountPixId);



        return UpdatePixResponse.fromUpdatePixRequest(pixOptional.get().getUniqueID(), accountPixId);

    }
}
