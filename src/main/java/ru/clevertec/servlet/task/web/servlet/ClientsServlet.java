package ru.clevertec.servlet.task.web.servlet;

import ru.clevertec.json.parser.JSONParser;
import ru.clevertec.servlet.task.context.ApplicationContext;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.exception.ClientValidateException;
import ru.clevertec.servlet.task.service.ClientService;
import ru.clevertec.servlet.task.util.Util;
import ru.clevertec.servlet.task.validator.ClientValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/v1/clients")
public class ClientsServlet extends HttpServlet {

	private final static String BAD_PARAMETER = "Bad parameter: sizepage or page.";
	private final ClientService clientService = (ClientService) ApplicationContext.getBean(ClientService.class);
	private final JSONParser jsonParser = (JSONParser) ApplicationContext.getBean(JSONParser.class);
	private final ClientValidator clientValidator = (ClientValidator) ApplicationContext.getBean(ClientValidator.class);

	/**
	 * Отображает всех Клиентов c пагинацией и сортировкой.
	 * Сортировать можно по имени (sorting = "first_name" - по умолчению),
	 * по дате рождения (sorting = "birthday") и по дате регистрации
	 * (sorting = "registration_date").
	 * Параметр page - номер страницы, параметр pagesize -
	 * количество Клиентов на одной странице.
	 * Если параметры pagesize или page заданы не верно
	 * выводится соответствующее сообщение.
	 *
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String request;
		int status;
		int pagesize = Integer.parseInt(req.getParameter("pagesize"));
		int page = Integer.parseInt(req.getParameter("page"));
		String sorting = req.getParameter("sorting");
		if (pagesize >= 0 & page > 0) {
			try {
				List<ClientDto> clients = clientService.getClientsWithPagination(pagesize, page, sorting);
				request = clients.stream()
								 .map(jsonParser::convertObjectToJson)
								 .collect(Collectors.joining("\n"));
				status = 200;
			} catch (Exception e) {
				request = e.getMessage();
				status = 404;
			}
		} else {
			status = 400;
			request = BAD_PARAMETER;
		}
		answerForServer(resp, status, request);
	}

	/**
	 * Принимает строку в формате Json, трансформирует ее в объект ClientDto и
	 * валидирует его на корректность данных, если валидация не проходит -
	 * выкидывает исключение ClientValidateException с ошибкой о том, какие данные
	 * не правильные
	 *
	 * Передает провалидированный объект на сервис, если валидация прошла успешно,
	 * для сохранения Клиента
	 *
	 * @throws ClientValidateException если Клиент не прошел валидацию
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String request;
		int status;
		try {
			String json = Util.readInputStream(req.getInputStream());
			ClientDto clientToCreate = jsonParser.convertJsonToObject(json, ClientDto.class);
			String errorMessages = clientValidator.validateProduct(clientToCreate);
			if (!errorMessages.isEmpty()) {
				throw new ClientValidateException(errorMessages);
			}
			clientService.create(clientToCreate);
			request = jsonParser.convertObjectToJson(clientToCreate);
			status = 201;
		} catch (ClientValidateException e) {
			request = e.getMessage();
			status = 400;
		} catch (Exception e) {
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
