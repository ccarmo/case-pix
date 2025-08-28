package com.pix.poc.application.usecase;

import com.pix.poc.application.web.dto.request.PixFilterRequest;
import com.pix.poc.application.web.dto.response.GetPixResponse;


import java.util.List;

public interface GetPixUseCase {

    List<GetPixResponse> getPix(PixFilterRequest request);
}
