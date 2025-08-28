package com.pix.poc.application.web.validator;

import com.pix.poc.application.web.dto.request.PixFilterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PixFilterRequestValidator implements ConstraintValidator<ValidPixFilter, PixFilterRequest> {

    private static final Logger logger = LoggerFactory.getLogger(PixFilterRequestValidator.class);

    @Override
    public boolean isValid(PixFilterRequest filter, ConstraintValidatorContext context) {
        if (filter == null) {
            logger.info("PixFilterRequest is null, skipping validation.");
            return true;
        }

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        logger.info("Starting validation for PixFilterRequest: {}", filter);

        if (filter.getId() == null &&
                (filter.getPixType() != null ||
                        filter.getPixInclusionDate() != null ||
                        filter.getPixDeactivationDate() != null ||
                        filter.getAgencyNumber() != null ||
                        filter.getAccountNumber() != null ||
                        filter.getName() != null)
        ) {
            logger.info("Validation passed: ID is null, other fields are allowed.");
            return true;
        }

        if (filter.getId() != null &&
                (filter.getPixType() != null ||
                        filter.getPixInclusionDate() != null ||
                        filter.getPixDeactivationDate() != null ||
                        filter.getAgencyNumber() != null ||
                        filter.getAccountNumber() != null ||
                        filter.getName() != null)) {

            logger.warn("Validation failed: ID is present, but other fields are not null.");
            context.buildConstraintViolationWithTemplate(
                            "Se o parametro 'id' estiver presente, todos os outros devem ser nulos"
                    )
                    .addPropertyNode("id")
                    .addConstraintViolation();

            valid = false;
        }

        if (filter.getPixInclusionDate() != null && filter.getPixDeactivationDate() != null) {
            logger.warn("Validation failed: Both pixInclusionDate and pixDeactivationDate are present.");
            context.buildConstraintViolationWithTemplate(
                            "Não pode enviar 'pixInclusionDate' e 'pixDeactivationDate' em conjunto. Apenas um ou outro."
                    )
                    .addPropertyNode("pixInclusionDate")
                    .addConstraintViolation();

            valid = false;
        }

        if (valid) {
            logger.info("PixFilterRequest validation passed.");
        } else {
            logger.error("PixFilterRequest validation failed.");
        }

        return valid;
    }
}