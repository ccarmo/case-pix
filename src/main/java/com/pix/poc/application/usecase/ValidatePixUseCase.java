package com.pix.poc.application.usecase;

import com.pix.poc.domain.entities.Pix;

import java.util.Optional;

public interface ValidatePixUseCase {

    Pix validatePix(String id);
}
