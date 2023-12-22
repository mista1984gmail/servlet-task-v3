package ru.clevertec.servlet.task.exception;

public class ClientValidateException extends ApplicationException {

    /**
     * Ошибка когда, объект Клиент не прошел валидацию
     *
     * @param message - сообщение об ошибке
     */
    public ClientValidateException(String message) {
        super(message);
    }

}
