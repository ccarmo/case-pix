package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.GetPixUseCase;
import com.pix.poc.application.usecase.ValidatePixUseCase;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.exception.PixNotFoundException;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.interactors.web.dto.request.PixFilterRequest;
import com.pix.poc.interactors.web.dto.response.GetPixResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPixUseCaseImpl implements GetPixUseCase {

    PixRepository pixRepository;
    ValidatePixUseCase validatePixUseCase;

    public GetPixUseCaseImpl(PixRepository pixRepository, ValidatePixUseCase validatePixUseCase){
        this.pixRepository = pixRepository;
        this.validatePixUseCase = validatePixUseCase;
    }

    @Override
    public List<GetPixResponse> getPix(PixFilterRequest pixFilterRequest) {
        validatePixUseCase.validatePix(pixFilterRequest.getId());
       List<Pix>  listPix = pixRepository.get(
               pixFilterRequest.getId(),
               pixFilterRequest.getPixType(),
               pixFilterRequest.getId(),
               pixFilterRequest.getAgencyNumber(),
               pixFilterRequest.getAccountNumber(),
               pixFilterRequest.getName(),
               pixFilterRequest.getPixInclusionDate(),
               pixFilterRequest.getPixDeactivationDate()
       );

       List<GetPixResponse> getPixResponse = GetPixResponse.fromPixList(listPix);

       if(getPixResponse.isEmpty()) {
           throw new PixNotFoundException("Nao há valor de pix para os parametro(s) pesquisado(s)");
       }

       return getPixResponse;
    }
}
