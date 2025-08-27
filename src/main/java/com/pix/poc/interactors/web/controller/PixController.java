package com.pix.poc.interactors.web.controller;

import com.pix.poc.application.usecase.CreatePixUseCase;


import com.pix.poc.application.usecase.DeletionUseCase;
import com.pix.poc.application.usecase.GetPixUseCase;

import com.pix.poc.application.usecase.UpdateUseCase;
import com.pix.poc.interactors.web.dto.request.CreatePixRequest;
import com.pix.poc.interactors.web.dto.request.PixFilterRequest;
import com.pix.poc.interactors.web.dto.request.UpdatePixRequest;
import com.pix.poc.interactors.web.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pix")
public class PixController {

    CreatePixUseCase createPixUseCase;
    GetPixUseCase getPixUseCase;
    UpdateUseCase updateUseCase;
    DeletionUseCase deletionUseCase;

    public PixController(CreatePixUseCase createPixUseCase, GetPixUseCase getPixUseCase, UpdateUseCase updateUseCase, DeletionUseCase deletionUseCase) {
        this.createPixUseCase = createPixUseCase;
        this.getPixUseCase = getPixUseCase;
        this.updateUseCase = updateUseCase;
        this.deletionUseCase = deletionUseCase;
    }

    @PostMapping
    public PixResponse create(@RequestBody CreatePixRequest createPixRequest) {
        SavePixResponse pixResponse = createPixUseCase.createPix(createPixRequest);
        return PixResponse.success(List.of(pixResponse.id()));
    }

    @GetMapping
    public PixResponse get(@Valid PixFilterRequest filterRequest) {
         List<GetPixResponse> getPixResponses = getPixUseCase.getPix(filterRequest);
         return PixResponse.success(getPixResponses);
    }

    @PatchMapping
    public PixResponse update(@RequestBody UpdatePixRequest updatePixRequest) {
        UpdatePixResponse getPixResponse = updateUseCase.changePix(updatePixRequest);
        return PixResponse.success(List.of(getPixResponse));
    }

    @DeleteMapping("/{id}")
    public PixResponse delete(@PathVariable("id") String id) {
        DeletionPixResponse deletionPixResponse = deletionUseCase.deletePix(id);
        return PixResponse.success(List.of(deletionPixResponse));
    }


}