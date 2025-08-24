package com.pix.poc.application.usecase;

import com.pix.poc.domain.entities.Pix;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public interface CreatePixUseCase {
    void createPix(Pix pix);
}
