package com.pix.poc.application.usecase;

import com.pix.poc.interactors.web.dto.request.UpdatePixRequest;
import com.pix.poc.interactors.web.dto.response.UpdatePixResponse;

public interface UpdateUseCase {

    UpdatePixResponse changePix(UpdatePixRequest updatePixRequest);
}
