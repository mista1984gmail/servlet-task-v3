package ru.clevertec.servlet.task.exception;

public class ClientNotFoundException extends ApplicationException {

	/**
	 * Сообщение должно быть именно такого формата
	 *
	 * @param id - идентификатор продукта
	 */
	public ClientNotFoundException(Long id) {
		super(String.format("Client with id: %s not found", id));
	}

}
