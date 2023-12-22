package ru.clevertec.servlet.task.validator;

import ru.clevertec.servlet.task.security.entity.dto.UserCredentialDto;

public interface UserValidator {

    boolean isNotEmail(UserCredentialDto user);
    boolean isPassword5AndMore10Characters(UserCredentialDto user);
    String validateUser(UserCredentialDto user);


}
