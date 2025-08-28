package com.pix.poc.application.usecase;

import com.pix.poc.application.web.dto.request.UpdatePixRequest;
import com.pix.poc.application.web.dto.response.UpdatePixResponse;

public interface UpdateUseCase {

    UpdatePixResponse changePix(UpdatePixRequest updatePixRequest);
}
