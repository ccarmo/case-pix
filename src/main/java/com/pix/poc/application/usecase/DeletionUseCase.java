package com.pix.poc.application.usecase;

import com.pix.poc.application.web.dto.response.DeletionPixResponse;

public interface DeletionUseCase {

    DeletionPixResponse deletePix(String id);
}
