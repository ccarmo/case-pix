package com.pix.poc.interactors.web.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PixFilterRequestValidator.class)
public @interface ValidPixFilter {
    String message() default "PixFilter inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
