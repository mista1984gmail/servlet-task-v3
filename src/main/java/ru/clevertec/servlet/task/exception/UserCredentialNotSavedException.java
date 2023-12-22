package ru.clevertec.servlet.task.exception;

public class UserCredentialNotSavedException extends ApplicationException {
	/**
	 * Ошибка когда, объект Пользователь не был сохранен
	 *
	 * @param email - сообщение об ошибке
	 */
	public UserCredentialNotSavedException(String email) {
		super(String.format("User credential with email: %s  did not save!!!", email));
	}

}
