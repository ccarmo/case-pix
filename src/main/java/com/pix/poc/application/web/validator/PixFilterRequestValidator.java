package com.pix.poc.application.web.validator;

import com.pix.poc.application.web.dto.request.PixFilterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PixFilterRequestValidator implements ConstraintValidator<ValidPixFilter, PixFilterRequest> {

    @Override
    public boolean isValid(PixFilterRequest filter, ConstraintValidatorContext context) {
        if (filter == null) return true;

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if (filter.getId() == null &&
                (filter.getPixType() != null ||
                        filter.getPixInclusionDate() != null ||
                        filter.getPixDeactivationDate() != null ||
                        filter.getAgencyNumber() != null ||
                        filter.getAccountNumber() != null ||
                        filter.getName() != null)
        ) {

            return true;
        }

        // Se id estiver presente, os outros campos devem ser nulos
        if (filter.getId() != null &&
                (filter.getPixType() != null ||
                        filter.getPixInclusionDate() != null ||
                        filter.getPixDeactivationDate() != null ||
                        filter.getAgencyNumber() != null ||
                        filter.getAccountNumber() != null ||
                        filter.getName() != null)) {

            context.buildConstraintViolationWithTemplate(
                            "Se o parametro 'id' estiver presente, todos os outros devem ser nulos"
                    )
                    .addPropertyNode("id") // indica que a violação está no campo 'id'
                    .addConstraintViolation();

            valid = false;
        }

        // Regra: pixInclusionDate e pixDeactivationDate não podem estar juntos
        if (filter.getPixInclusionDate() != null && filter.getPixDeactivationDate() != null) {
            context.buildConstraintViolationWithTemplate(
                            "Não pode enviar 'pixInclusionDate' e 'pixDeactivationDate' em conjunto. Apenas um ou outro."
                    )
                    .addPropertyNode("pixInclusionDate") // ou "pixDeactivationDate"
                    .addConstraintViolation();

            valid = false;
        }



        return valid;
    }
}