package ru.clevertec.servlet.task.validator.impl;

import ru.clevertec.servlet.task.security.entity.dto.UserCredentialDto;
import ru.clevertec.servlet.task.validator.UserValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class UserValidatorImpl implements UserValidator {

	/**
	 * Проверяет электронный адрес Пользователя на корректность
	 *
	 * @param user Dto
	 * @return true, если электронный адрес не корректный, иначе false
	 */
	@Override
	public boolean isNotEmail(UserCredentialDto user) {
		return !user.getEmail().matches("^\\S+@\\S+\\.\\S+$");
	}

	/**
	 * Проверяет пароль Пользователя
	 *
	 * @param user Dto
	 * @return true, если длина пароля Пользователя меньше 5 символов или больше 10, иначе false
	 */
	@Override
	public boolean isPassword5AndMore10Characters(UserCredentialDto user) {
		return user.getPassword().length() < 5 || user.getPassword().length() > 10;
	}

	public Map<String, Predicate<UserCredentialDto>> getCheckListForUser() {
		Map<String, Predicate<UserCredentialDto>> checklistClient = new HashMap<>();

		Predicate<UserCredentialDto> isNotEmail = this::isNotEmail;
		Predicate<UserCredentialDto> isPasswordLongLess5AndMore10Characters = this::isPassword5AndMore10Characters;

		checklistClient.put("Client email is not correct!!!", isNotEmail);
		checklistClient.put("Client password name is not 5-10 characters long!!!", isPasswordLongLess5AndMore10Characters);

		return checklistClient;

	}

	@Override
	public String validateUser(UserCredentialDto user) {
		return String.join("\n", getCheckListForUser().entrySet()
														.stream()
														.filter(entry -> entry.getValue()
																			  .test(user))
														.map(Map.Entry::getKey)
														.toList());
	}

}
