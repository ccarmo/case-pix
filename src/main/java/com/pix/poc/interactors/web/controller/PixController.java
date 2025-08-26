package com.pix.poc.interactors.web.controller;

import com.pix.poc.application.usecase.CreatePixUseCase;


import com.pix.poc.application.usecase.GetPixUseCase;

import com.pix.poc.interactors.web.dto.request.CreatePixRequest;
import com.pix.poc.interactors.web.dto.request.PixFilterRequest;
import com.pix.poc.interactors.web.dto.response.GetPixResponse;
import com.pix.poc.interactors.web.dto.response.ResponsePixCustom;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pix")
public class PixController {

    CreatePixUseCase createPixUseCase;
    GetPixUseCase getPixUseCase;

    public PixController(CreatePixUseCase createPixUseCase, GetPixUseCase getPixUseCase) {
        this.createPixUseCase = createPixUseCase;
        this.getPixUseCase = getPixUseCase;
    }

    @PostMapping
    public ResponsePixCustom createPix(@RequestBody CreatePixRequest createPixRequest) {
        String uuidString = createPixUseCase.createPix(createPixRequest.toPix());
        return ResponsePixCustom.success(List.of(uuidString));
    }

    @GetMapping
    public ResponsePixCustom get(@Valid PixFilterRequest filterRequest) {
         List<GetPixResponse> list = getPixUseCase.getPix(filterRequest);
         return ResponsePixCustom.success(list);
    }
}