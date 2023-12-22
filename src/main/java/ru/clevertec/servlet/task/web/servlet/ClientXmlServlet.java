package ru.clevertec.servlet.task.web.servlet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

@WebServlet("/api/v1/clients_xml")
public class ClientXmlServlet extends HttpServlet {

	private final ClientService clientService = (ClientService) ApplicationContext.getBean(ClientService.class);
	private final JSONParser jsonParser = (JSONParser) ApplicationContext.getBean(JSONParser.class);
	private final XmlMapper xmlMapper = (XmlMapper) ApplicationContext.getBean(XmlMapper.class);
	private final ClientValidator clientValidator = (ClientValidator) ApplicationContext.getBean(ClientValidator.class);

	/**
	 * Принимает строку в формате xml, трансформирует ее в объект ClientDto и
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
			String xml = Util.readInputStream(req.getInputStream());
			ClientDto clientToCreate = xmlMapper.readValue(xml, ClientDto.class);
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
