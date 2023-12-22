package ru.clevertec.servlet.task.exception;

public class UserValidateException extends ApplicationException {

    /**
     * Ошибка когда, объект Пользователь не прошел валидацию
     *
     * @param message - сообщение об ошибке
     */
    public UserValidateException(String message) {
        super(message);
    }

}
