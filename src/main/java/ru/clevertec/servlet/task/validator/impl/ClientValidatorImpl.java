package ru.clevertec.servlet.task.validator.impl;


import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.validator.ClientValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ClientValidatorImpl implements ClientValidator {

    /**
     * Проверяет имя Клиента на null или empty
     *
     * @param client Dto
     * @return true, если имя Клиента null или оно empty, иначе false
     */
    @Override
    public boolean isFirstNameNullOrEmpty(ClientDto client) {
        return client.getFirstName() == null || client.getFirstName()
                                                      .isEmpty();
    }

    /**
     * Проверяет фамилию Клиента на null или empty
     *
     * @param client Dto
     * @return true, если фамилия Клиента null или она empty, иначе false
     */
    @Override
    public boolean isLastNameNullOrEmpty(ClientDto client) {
        return client.getLastName() == null || client.getLastName()
                                                     .isEmpty();
    }

    /**
     * Проверяет длину имени Клиента
     *
     * @param client Dto
     * @return true, если длина имени Клиента меньше 2 символов или больше 10, иначе false
     */
    @Override
    public boolean isFirstNameLongLess2AndMore10Characters(ClientDto client) {
        return client.getFirstName()
                     .length() < 2 || client.getFirstName()
                                            .length() > 10;
    }

    /**
     * Проверяет фамилию Клиента
     *
     * @param client Dto
     * @return true, если длина фамилии Клиента меньше 2 символов или больше 30, иначе false
     */
    @Override
    public boolean isLastNameLongLess2AndMore30Characters(ClientDto client) {
        return client.getLastName()
                     .length() < 2 || client.getLastName()
                                            .length() > 30;
    }

    /**
     * Проверяет электронный адрес Клиента на корректность
     *
     * @param client Dto
     * @return true, если электронный адрес не корректный, иначе false
     */
    @Override
    public boolean isNotEmail(ClientDto client) {
        return !client.getEmail()
                      .matches("^\\S+@\\S+\\.\\S+$");
    }

    /**
     * Проверяет телефон Клиента на наличие "+" в начале
     *
     * @param client Dto
     * @return true, если телефон Клиента не начинается со знака "+", иначе false
     */
    @Override
    public boolean isTelephoneDoesNotStartPlus(ClientDto client) {
        return !client.getTelephone()
                      .matches("^\\+\\d*");
    }

    /**
     * Проверяет телефон Клиента на наличие 12 цифр в номере телефона
     *
     * @param client Dto
     * @return true, если телефон Клиента не имеет 12 цифр, иначе false
     */
    @Override
    public boolean isTelephoneDoesNotConsist12Digits(ClientDto client) {
        return !client.getTelephone()
                      .matches("^\\+\\d{12}");
    }

    public Map<String, Predicate<ClientDto>> getCheckListForClient() {
        Map<String, Predicate<ClientDto>> checklistClient = new HashMap<>();

        Predicate<ClientDto> isClientFirstNameNullOrEmpty = this::isFirstNameNullOrEmpty;
        Predicate<ClientDto> isClientLastNameNullOrEmpty = this::isLastNameNullOrEmpty;
        Predicate<ClientDto> isClientFirstNameLongLess2AndMore10Characters = this::isFirstNameLongLess2AndMore10Characters;
        Predicate<ClientDto> isClientLastNameLongLess2AndMore30Characters = this::isLastNameLongLess2AndMore30Characters;
        Predicate<ClientDto> isNotEmail = this::isNotEmail;
        Predicate<ClientDto> isClientTelephoneDoesNotStartPlus = this::isTelephoneDoesNotStartPlus;
        Predicate<ClientDto> isClientTelephoneDoesNotConsist12Digits = this::isTelephoneDoesNotConsist12Digits;

        checklistClient.put("Client first name is null or empty!!!", isClientFirstNameNullOrEmpty);
        checklistClient.put("Client last name is null or empty!!!", isClientLastNameNullOrEmpty);
        checklistClient.put("Client first name is not 2-10 characters long!!!", isClientFirstNameLongLess2AndMore10Characters);
        checklistClient.put("Client last name is not 2-30 characters long!!!", isClientLastNameLongLess2AndMore30Characters);
        checklistClient.put("Client email is not correct!!!", isNotEmail);
        checklistClient.put("Client telephone does not start with +!", isClientTelephoneDoesNotStartPlus);
        checklistClient.put("Client telephone does not have 12 digits!!!", isClientTelephoneDoesNotConsist12Digits);

        return checklistClient;

    }

    /**
     * Производит валидицию Клиента
     *
     * @param client Клиент
     * @return String со списком ошибок валидации, если ошибок нет - пустой String
     */
    @Override
    public String validateProduct(ClientDto client) {
        return String.join("\n", getCheckListForClient().entrySet()
                                                        .stream()
                                                        .filter(entry -> entry.getValue()
                                                                              .test(client))
                                                        .map(Map.Entry::getKey)
                                                        .toList());
    }

}
