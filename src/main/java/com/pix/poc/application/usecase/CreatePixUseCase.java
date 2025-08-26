package com.pix.poc.application.usecase;

import com.pix.poc.domain.entities.Pix;

import java.util.UUID;

public interface CreatePixUseCase {
    String createPix(Pix pix);
}
