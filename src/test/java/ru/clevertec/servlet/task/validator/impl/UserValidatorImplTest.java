package ru.clevertec.servlet.task.validator.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.servlet.task.security.entity.dto.UserCredentialDto;
import ru.clevertec.servlet.task.util.UserTestData;
import ru.clevertec.servlet.task.validator.UserValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidatorImplTest {

	private UserValidator userValidator;

	@BeforeEach
	void setup() {
		userValidator = new UserValidatorImpl();
	}

	@ParameterizedTest
	@ValueSource(strings = {"ivan2000com.ru", "ivan2000@", "ivan2000comru", "ivan2000@comru", "@com.ru"})
	void shouldReturnTrue_whenUserEmailIsNotCorrect(String email) {
		// given
		UserCredentialDto user = UserTestData.builder()
											 .withEmail(email)
											 .build()
											 .buildUserCredentialDto();

		//when
		boolean actual = userValidator.isNotEmail(user);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnFalse_whenUserEmailIsCorrect() {
		// given
		UserCredentialDto user = UserTestData.builder()
											 .build()
											 .buildUserCredentialDto();

		//when
		boolean actual = userValidator.isNotEmail(user);

		//then
		assertFalse(actual);
	}

	@Test
	void shouldReturnTrue_whenUserPasswordIsLongMore10Characters() {
		// given
		UserCredentialDto user = UserTestData.builder()
											 .withPassword("12345678910")
											 .build()
											 .buildUserCredentialDto();

		//when
		boolean actual = userValidator.isPassword5AndMore10Characters(user);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnFalse_whenUserPasswordIsNotLongLess5AndMore10Characters() {
		// given
		UserCredentialDto user = UserTestData.builder()
											 .build()
											 .buildUserCredentialDto();

		//when
		boolean actual = userValidator.isPassword5AndMore10Characters(user);

		//then
		assertFalse(actual);
	}

	@Test
	void shouldReturnTrue_whenUserPasswordIsLongLess5Characters() {
		// given
		UserCredentialDto user = UserTestData.builder()
											 .withPassword("1234")
											 .build()
											 .buildUserCredentialDto();

		//when
		boolean actual = userValidator.isPassword5AndMore10Characters(user);

		//then
		assertTrue(actual);
	}
}