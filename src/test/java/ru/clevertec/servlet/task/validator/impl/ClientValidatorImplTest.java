package ru.clevertec.servlet.task.validator.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.util.ClientTestData;
import ru.clevertec.servlet.task.validator.ClientValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientValidatorImplTest {

	private ClientValidator clientValidator;

	@BeforeEach
	void setup() {
		clientValidator = new ClientValidatorImpl();
	}

	@Test
	void shouldReturnTrue_whenClientFirstNameIsNull() {
		// given
		ClientDto client = ClientTestData.builder()
										 .withFirstName(null)
										 .build()
										 .buildClientDto();


		//when
		boolean actual = clientValidator.isFirstNameNullOrEmpty(client);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnFalse_whenClientFirstNameIsNotNull() {
		// given
		ClientDto client = ClientTestData.builder()
										 .build()
										 .buildClientDto();


		//when
		boolean actual = clientValidator.isFirstNameNullOrEmpty(client);

		//then
		assertFalse(actual);
	}

	@Test
	void shouldReturnTrue_whenClientLastNameIsNull() {
		// given
		ClientDto client = ClientTestData.builder()
										 .withLastName(null)
										 .build()
										 .buildClientDto();


		//when
		boolean actual = clientValidator.isLastNameNullOrEmpty(client);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnFalse_whenClientLastNameIsNotNull() {
		// given
		ClientDto client = ClientTestData.builder()
										 .build()
										 .buildClientDto();


		//when
		boolean actual = clientValidator.isLastNameNullOrEmpty(client);

		//then
		assertFalse(actual);
	}

	@Test
	void shouldReturnFalse_whenClientFirstNameIsNotLongLess2AndMore10Characters() {
		// given
		ClientDto client = ClientTestData.builder()
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isFirstNameLongLess2AndMore10Characters(client);

		//then
		assertFalse(actual);
	}

	@Test
	void shouldReturnTrue_whenClientFirstNameIsLongLess2Characters() {
		// given
		ClientDto client = ClientTestData.builder()
										 .withFirstName("A")
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isFirstNameLongLess2AndMore10Characters(client);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnTrue_whenClientFirstNameIsLongMore10Characters() {
		// given
		ClientDto client = ClientTestData.builder()
										 .withFirstName("Абдульвадуд")
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isFirstNameLongLess2AndMore10Characters(client);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnFalse_whenClientLastNameIsNotLongLess2AndMore30Characters() {
		// given
		ClientDto client = ClientTestData.builder()
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isLastNameLongLess2AndMore30Characters(client);

		//then
		assertFalse(actual);
	}

	@Test
	void shouldReturnFalse_whenClientLastNameIsLongLess2Characters() {
		// given
		ClientDto client = ClientTestData.builder()
										 .withLastName("A")
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isLastNameLongLess2AndMore30Characters(client);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnTrue_whenClientLastNameIsLongMore30Characters() {
		// given
		ClientDto client = ClientTestData.builder()
										 .withLastName("Абдульвадудвбдульвадудабдульвадуд")
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isLastNameLongLess2AndMore30Characters(client);

		//then
		assertTrue(actual);
	}

	@ParameterizedTest
	@ValueSource(strings = {"ivan2000com.ru", "ivan2000@", "ivan2000comru", "ivan2000@comru", "@com.ru"})
	void shouldReturnTrue_whenClientEmailIsNotCorrect(String email) {
		// given
		ClientDto client = ClientTestData.builder()
										 .withEmail(email)
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isNotEmail(client);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnFalse_whenClientEmailIsCorrect() {
		// given
		ClientDto client = ClientTestData.builder()
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isNotEmail(client);

		//then
		assertFalse(actual);
	}

	@Test
	void shouldReturnTrue_whenClientTelephoneDoesNotStartPlus() {
		// given
		ClientDto client = ClientTestData.builder()
										 .withTelephone("375291234567")
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isTelephoneDoesNotStartPlus(client);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnFalse_whenClientTelephoneStartPlus() {
		// given
		ClientDto client = ClientTestData.builder()
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isTelephoneDoesNotStartPlus(client);

		//then
		assertFalse(actual);
	}

	@Test
	void shouldReturnTrue_whenClientTelephoneDoesNotConsist12Digits() {
		// given
		ClientDto client = ClientTestData.builder()
										 .withTelephone("+37529123456")
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isTelephoneDoesNotConsist12Digits(client);

		//then
		assertTrue(actual);
	}

	@Test
	void shouldReturnFalse_whenClientTelephoneConsist12Digits() {
		// given
		ClientDto client = ClientTestData.builder()
										 .build()
										 .buildClientDto();

		//when
		boolean actual = clientValidator.isTelephoneDoesNotConsist12Digits(client);

		//then
		assertFalse(actual);
	}
}