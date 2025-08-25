package com.pix.poc.interactors.web.controller;

import com.pix.poc.application.usecase.CreatePixUseCase;


import com.pix.poc.interactors.web.dto.request.CreatePixRequest;
import com.pix.poc.interactors.web.dto.response.ResponsePixCustom;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    private CreatePixUseCase createPixUseCase;

    public PixController(CreatePixUseCase createPixUseCase) {
        this.createPixUseCase = createPixUseCase;
    }

    @PostMapping
    public ResponsePixCustom createPix(@RequestBody CreatePixRequest createPixRequest) {
        createPixUseCase.createPix(createPixRequest.toPix());
        return ResponsePixCustom.success("Pix cadastrado com sucesso");
    }
}