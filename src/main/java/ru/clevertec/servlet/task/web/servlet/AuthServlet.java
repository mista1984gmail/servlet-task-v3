package ru.clevertec.servlet.task.web.servlet;

import ru.clevertec.json.parser.JSONParser;
import ru.clevertec.servlet.task.context.ApplicationContext;
import ru.clevertec.servlet.task.exception.UserValidateException;
import ru.clevertec.servlet.task.security.entity.dto.UserCredentialDto;
import ru.clevertec.servlet.task.security.service.AuthService;
import ru.clevertec.servlet.task.util.Util;
import ru.clevertec.servlet.task.validator.UserValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/authentication")
public class AuthServlet extends HttpServlet {

	private final static String USER_SAVED = "User saved";
	private final static String USER_NOT_SAVED = "User not saved";

	private final AuthService authService = (AuthService) ApplicationContext.getBean(AuthService.class);
	private final UserValidator userValidator = (UserValidator) ApplicationContext.getBean(UserValidator.class);
	private final JSONParser jsonParser = (JSONParser) ApplicationContext.getBean(JSONParser.class);

	/**
	 * Проводит аутентификацию Пользователя,
	 * если аутентификация прошла успешно - возращает token
	 * для доступа к другим ресурсам.
	 *
	 * @throws UserValidateException если Пользователь не прошел валидацию
	 * @throws Exception если Пользователь не найден
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String request;
		int status;
		try {
			String json = Util.readInputStream(req.getInputStream());
			UserCredentialDto userCredential = jsonParser.convertJsonToObject(json, UserCredentialDto.class);
			String errorMessages = userValidator.validateUser(userCredential);
			if (!errorMessages.isEmpty()) {
				throw new UserValidateException(errorMessages);
			}
			request = authService.authorization(userCredential);
			status = 200;
		}
		catch (UserValidateException e) {
			request = e.getMessage();
			status = 400;
		}
		catch (Exception e) {
			request = e.getMessage();
			status = 404;
		}
		answerForServer(resp, status, request);
	}

	/**
	 * Принимает строку в формате Json, трансформирует ее в объект UserCredentialDto и
	 * валидирует его на корректность данных, если валидация не проходит -
	 * выкидывает исключение UserValidateException с ошибкой о том, какие данные
	 * не правильные
	 *
	 * Передает провалидированный объект на сервис, если валидация прошла успешно,
	 * где происходит его сохранение
	 *
	 * @throws UserValidateException если Пользователь не прошел валидацию
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String request;
		int status;
		try {
			String json = Util.readInputStream(req.getInputStream());
			UserCredentialDto userCredential = jsonParser.convertJsonToObject(json, UserCredentialDto.class);
			String errorMessages = userValidator.validateUser(userCredential);
			if (!errorMessages.isEmpty()) {
				throw new UserValidateException(errorMessages);
			}
			if(authService.saveUser(userCredential)){
				request = USER_SAVED;
				status = 201;
			}else {
				request = USER_NOT_SAVED;
				status = 400;

			}
		}
		catch (UserValidateException e) {
			request = e.getMessage();
			status = 400;
		}
		catch (Exception e) {
			request = e.getMessage();
			status = 404;
		}
		answerForServer(resp, status, request);
	}

	/**
	 * Формирует и отправляет ответ на сервер
	 *
	 */
	private static void answerForServer(HttpServletResponse resp, int status, String request) throws IOException {
		resp.setStatus(status);
		resp.setHeader("Content-Type", "application/json");
		resp.getOutputStream().println(request);
	}

}
