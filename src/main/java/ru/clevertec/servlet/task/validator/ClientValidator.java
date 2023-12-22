package ru.clevertec.servlet.task.validator;

import ru.clevertec.servlet.task.entity.dto.ClientDto;

public interface ClientValidator {

    boolean isFirstNameNullOrEmpty(ClientDto client);

    boolean isLastNameNullOrEmpty(ClientDto client);

    boolean isFirstNameLongLess2AndMore10Characters(ClientDto client);

    boolean isLastNameLongLess2AndMore30Characters(ClientDto client);

    boolean isNotEmail(ClientDto client);

    boolean isTelephoneDoesNotStartPlus(ClientDto client);

    boolean isTelephoneDoesNotConsist12Digits(ClientDto client);

    String validateProduct(ClientDto client);

}
