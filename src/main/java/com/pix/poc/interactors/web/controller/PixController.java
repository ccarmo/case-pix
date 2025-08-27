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
import jakarta.websocket.server.PathParam;
import org.springframework.data.repository.query.Param;
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
    public ResponsePixCustom create(@RequestBody CreatePixRequest createPixRequest) {
        SavePixResponse pixResponse = createPixUseCase.createPix(createPixRequest);
        return ResponsePixCustom.success(List.of(pixResponse.id()));
    }

    @GetMapping
    public ResponsePixCustom get(@Valid PixFilterRequest filterRequest) {
         List<GetPixResponse> list = getPixUseCase.getPix(filterRequest);
         return ResponsePixCustom.success(list);
    }

    @PatchMapping
    public ResponsePixCustom update(@RequestBody UpdatePixRequest updatePixRequest) {
        UpdatePixResponse update = updateUseCase.changePix(updatePixRequest);
        return ResponsePixCustom.success(List.of(update));
    }

    @DeleteMapping("/{id}")
    public ResponsePixCustom delete(@PathVariable("id") String id) {
        DeletionPixResponse deletionPixResponse = deletionUseCase.deletePix(id);
        return ResponsePixCustom.success(List.of(deletionPixResponse));
    }


}