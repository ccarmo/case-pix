package com.pix.poc.application.usecase.impl;

import com.pix.poc.application.usecase.GetPixUseCase;
import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.exception.PixNotFoundException;
import com.pix.poc.domain.repository.PixRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.interactors.web.dto.request.PixFilterRequest;
import com.pix.poc.interactors.web.dto.response.GetPixResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetPixUseCaseImpl implements GetPixUseCase {

    PixRepository pixRepository;

    public GetPixUseCaseImpl(PixRepository pixRepository){
        this.pixRepository = pixRepository;
    }

    @Override
    public List<GetPixResponse> getPix(PixFilterRequest pixFilterRequest) {

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
