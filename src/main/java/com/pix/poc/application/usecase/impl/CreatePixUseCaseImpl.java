package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.CreatePixUseCase;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.repository.PixRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePixUseCaseImpl implements CreatePixUseCase {

    PixRepository pixRepository;

    public CreatePixUseCaseImpl(PixRepository pixRepository) {
        this.pixRepository = pixRepository;
    }

    @Override
    public void createPix(Pix pix) {
        pixRepository.save(pix);
    }
}
