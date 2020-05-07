package com.example.demo.constraints;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // cel putin 5 caractere
                new LengthRule(5,100),

                // cel putin o litera mare
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // cel putin o litera mica
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // cel putin o cifra
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // cel putin un caracter special
                new CharacterRule(EnglishCharacterData.Special, 1),

                // fara spatii albe
                new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        if(result.isValid()){
            return true;
        }

        List<String> messages = validator.getMessages(result);

        String messageTemplate = messages.stream()
                .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
