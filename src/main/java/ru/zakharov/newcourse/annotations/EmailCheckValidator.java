package ru.zakharov.newcourse.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailCheckValidator implements ConstraintValidator<EmailCheck, String> {

    private String prefix;

    @Override
    public void initialize(EmailCheck constraintAnnotation) {
        prefix = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        if (code != null) {
            if (!code.endsWith(prefix)) {
                result = false;
            }
        }
        return result;
    }
}
