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

    public String getValue() {
        return value;
    }

    public DocumentType getType() {
        return type;
    }

    private boolean isValidCPF(String cpf) {

        if (cpf == null || cpf.length() != 11 || !cpf.matches("[0-9]{11}")) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false; // Não permite CPFs com números repetidos
        }

        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }

        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) digito1 = 0;

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }

        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) digito2 = 0;

        return digito1 == Character.getNumericValue(cpf.charAt(9)) && digito2 == Character.getNumericValue(cpf.charAt(10));


    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || !cnpj.matches("[0-9]{14}")) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int soma = 0;
        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
        }

        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) digito1 = 0;

        soma = 0;
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
        }

        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) digito2 = 0;

        return digito1 == Character.getNumericValue(cnpj.charAt(12)) && digito2 == Character.getNumericValue(cnpj.charAt(13));
    }
}
