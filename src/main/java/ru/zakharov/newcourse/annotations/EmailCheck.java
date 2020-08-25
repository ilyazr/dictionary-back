package ru.zakharov.newcourse.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailCheckValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailCheck {

    String value() default "@hello.com";
    String message() default "Обязателен домен @hello.com!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
