package ru.clevertec.servlet.task.util;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
public class ClientTestData {

	@Builder.Default
	private Long id = ConstantsForTest.ID;

	@Builder.Default
	private String firstName = ConstantsForTest.CLIENT_FIRST_NAME;

	@Builder.Default
	private String lastName = ConstantsForTest.CLIENT_SECOND_NAME;

	@Builder.Default
	private String email = ConstantsForTest.CLIENT_EMAIL;

	@Builder.Default
	private String telephone = ConstantsForTest.CLIENT_TELEPHONE;

	@Builder.Default
	private LocalDate birthday = ConstantsForTest.CLIENT_BIRTHDAY;

	@Builder.Default
	private LocalDateTime registrationDate = ConstantsForTest.CLIENT_REGISTRATION_DATE;

	public Client buildClient() {
		return new Client(id, firstName, lastName, email, telephone, birthday, registrationDate);
	}

	public ClientDto buildClientDto() {
		return new ClientDto(firstName, lastName, email, telephone, birthday, registrationDate);
	}

}
