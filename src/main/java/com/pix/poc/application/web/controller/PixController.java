package com.pix.poc.application.web.controller;

import com.pix.poc.application.usecase.CreatePixUseCase;


import com.pix.poc.application.usecase.DeletionUseCase;
import com.pix.poc.application.usecase.GetPixUseCase;

import com.pix.poc.application.usecase.UpdateUseCase;
import com.pix.poc.application.web.dto.request.CreatePixRequest;
import com.pix.poc.application.web.dto.request.PixFilterRequest;
import com.pix.poc.application.web.dto.request.UpdatePixRequest;
import com.pix.poc.application.web.dto.response.*;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pix")
public class PixController {

    private static final Logger logger = LoggerFactory.getLogger(PixController.class);

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
        logger.info("Received request to create Pix key: {}", createPixRequest);
        SavePixResponse pixResponse = createPixUseCase.createPix(createPixRequest);
        logger.info("Pix key created successfully with ID: {}", pixResponse.id());
        return PixResponse.success(List.of(pixResponse.id()));
    }

    @GetMapping
    public PixResponse get(@Valid PixFilterRequest filterRequest) {
        logger.info("Received request to retrieve Pix keys with filters: {}", filterRequest);
        List<GetPixResponse> getPixResponses = getPixUseCase.getPix(filterRequest);
        logger.info("Retrieved {} Pix keys", getPixResponses.size());
        return PixResponse.success(getPixResponses);
    }

    @PatchMapping
    public PixResponse update(@RequestBody UpdatePixRequest updatePixRequest) {
        logger.info("Received request to update Pix key: {}", updatePixRequest);
        UpdatePixResponse getPixResponse = updateUseCase.changePix(updatePixRequest);
        logger.info("Pix key updated successfully: {}", getPixResponse);
        return PixResponse.success(List.of(getPixResponse));
    }

    @DeleteMapping("/{id}")
    public PixResponse delete(@PathVariable("id") String id) {
        logger.info("Received request to delete Pix key with ID: {}", id);
        DeletionPixResponse deletionPixResponse = deletionUseCase.deletePix(id);
        logger.info("Pix key deleted successfully: {}", deletionPixResponse);
        return PixResponse.success(List.of(deletionPixResponse));
    }


}