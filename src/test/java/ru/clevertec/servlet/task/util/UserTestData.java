package ru.clevertec.servlet.task.util;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.servlet.task.security.entity.UserCredential;
import ru.clevertec.servlet.task.security.entity.dto.UserCredentialDto;

@Data
@Builder(setterPrefix = "with")
public class UserTestData {

	@Builder.Default
	private Long id = ConstantsForTest.ID;

	@Builder.Default
	private String email = ConstantsForTest.CLIENT_EMAIL;

	@Builder.Default
	private String password = ConstantsForTest.CLIENT_PASSWORD;

	public UserCredentialDto buildUserCredentialDto() {
		return new UserCredentialDto(email, password);
	}

	public UserCredential buildUserCredential() {
		return new UserCredential(id, email, password);
	}
}
