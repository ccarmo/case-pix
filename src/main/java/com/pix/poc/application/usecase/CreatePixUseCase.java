package com.pix.poc.application.usecase;

import com.pix.poc.domain.entities.Pix;
import com.pix.poc.interactors.web.dto.request.CreatePixRequest;
import com.pix.poc.interactors.web.dto.response.SavePixResponse;


public interface CreatePixUseCase {
    SavePixResponse createPix(CreatePixRequest pix);
}
