package com.pix.poc.interactors.web.validator;

import com.pix.poc.interactors.web.dto.request.PixFilterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PixFilterRequestValidator implements ConstraintValidator<ValidPixFilter, PixFilterRequest> {

    @Override
    public boolean isValid(PixFilterRequest filter, ConstraintValidatorContext context) {
        if (filter == null) return true;

        boolean validate = true;


        if (filter.getId() != null && (
                filter.getPixType() != null ||
                filter.getPixInclusionDate() != null ||
                filter.getPixDeactivationDate() != null ||
                filter.getAgencyNumber() != null ||
                filter.getAccountNumber() != null ||
                filter.getName() != null))  {
            context.buildConstraintViolationWithTemplate(
                    "Se o parametro 'id' estiver presente, todos os outros devem ser nulo"
            ).addConstraintViolation();
            validate = false;
        }

        if(filter.getPixInclusionDate() != null && filter.getPixDeactivationDate() != null) {
            context.buildConstraintViolationWithTemplate(
                    "Nao pode enviar parametro de 'pixInclusionDate' e 'pixDeactivationDate' em conjunto. Apenas um ou outro."
            ).addConstraintViolation();
            validate = false;
        }

        context.disableDefaultConstraintViolation();

        return validate;
    }
}
