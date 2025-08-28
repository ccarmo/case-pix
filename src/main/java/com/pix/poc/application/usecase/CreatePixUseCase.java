package com.pix.poc.application.usecase;

import com.pix.poc.application.web.dto.request.CreatePixRequest;
import com.pix.poc.application.web.dto.response.SavePixResponse;


public interface CreatePixUseCase {
    SavePixResponse createPix(CreatePixRequest pix);
}
