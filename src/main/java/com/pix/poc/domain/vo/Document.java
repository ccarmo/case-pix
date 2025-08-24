package com.pix.poc.domain.vo;

import com.pix.poc.domain.entities.DocumentType;
import com.pix.poc.domain.exception.InvalidDocumentException;

public class Document {

    private final String value;
    private final DocumentType type;


    public Document(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidDocumentException("Documento não pode ser vazio");
        }

        if (!value.matches("\\d+")) {
            throw new InvalidDocumentException("Documento deve conter apenas números, sem pontuação");
        }

        if (value.length() == 11) {
            if (!isValidCPF(value)) {
                throw new InvalidDocumentException("CPF inválido");
            }
            this.type = DocumentType.CPF;
        } else if (value.length() == 14) {
            if (!isValidCNPJ(value)) {
                throw new InvalidDocumentException("CNPJ inválido");
            }
            this.type = DocumentType.CNPJ;
        } else {
            throw new InvalidDocumentException("Documento deve ser CPF (11 dígitos) ou CNPJ (14 dígitos)");
        }

        this.value = value;
    }

    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{11}");
    }

    private boolean isValidCNPJ(String cnpj) {
        return cnpj.matches("\\d{14}");
    }
}
