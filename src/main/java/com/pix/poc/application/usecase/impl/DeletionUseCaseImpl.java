package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.DeletionUseCase;
import com.pix.poc.application.usecase.ValidatePixUseCase;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.interactors.web.dto.response.DeletionPixResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class DeletionUseCaseImpl implements DeletionUseCase {

    ValidatePixUseCase validatePixUseCase;
    PixRepository pixRepository;

    public DeletionUseCaseImpl(ValidatePixUseCase validatePixUseCase, PixRepository pixRepository) {
        this.validatePixUseCase = validatePixUseCase;
        this.pixRepository = pixRepository;
    }


    @Override
    public DeletionPixResponse deletePix(String id) {
        Pix pix = validatePixUseCase.validatePix(id);
        pix.setActive(false);
        pix.setInactivationDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        Pix pixUpdate = pixRepository.save(pix);
        return DeletionPixResponse.toDeletionPixResponse(pixUpdate);

    }
}
