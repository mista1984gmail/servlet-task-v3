package ru.clevertec.servlet.task.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;
import ru.clevertec.servlet.task.exception.ClientNotFoundException;
import ru.clevertec.servlet.task.mapper.ClientMapper;
import ru.clevertec.servlet.task.repository.ClientRepository;
import ru.clevertec.servlet.task.repository.ClientSortingRepository;
import ru.clevertec.servlet.task.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ClientSortingRepository clientSortingRepository;
	private final ClientMapper clientMapper;

	public ClientServiceImpl(ClientRepository clientRepository, ClientSortingRepository clientSortingRepository, ClientMapper clientMapper) {
		this.clientRepository = clientRepository;
		this.clientSortingRepository = clientSortingRepository;
		this.clientMapper = clientMapper;
	}

	/**
	 * Создаёт нового Клиента из DTO
	 *
	 * @param client DTO с информацией о создании
	 */
	@Override
	public Long create(ClientDto client) throws Exception {
		log.info("Сохранение КЛИЕНТА: {}", client);
		Client savedClient = clientRepository.save(clientMapper.clientDtoToClient(client));
		String success = savedClient.getId() != 0 ? "" : "не";
		log.info("КЛИЕНТ {} сохранен: {}", success, client);
		return savedClient.getId();
	}

	/**
	 * Возвращает всех существующих Клиентов
	 *
	 * @return лист с информацией о Клиентах
	 */
	@Override
	public List<ClientDto> getAll() throws Exception {
		return clientRepository.getAll()
							   .stream()
							   .map(clientMapper::clientToClientDto)
							   .collect(Collectors.toList());
	}

	/**
	 * Возвращает всех существующих Клиентов с пагинацией
	 * и сортировкой по имени, дате рождения и дате регистрации
	 *
	 * @param pagesize - сколько отобразить Клиентов на одном листе
	 * @param page - номер страницы
	 * @param sorting - данные для сортировки
	 * @return лист с информацией о Клиентах
	 */
	@Override
	public List<ClientDto> getClientsWithPagination(int pagesize, int page, String sorting) throws Exception {
		return clientMapper.clientsToClientsDto(clientSortingRepository.sortingClients(pagesize, page, sorting));
	}

	/**
	 * Ищет Клиента по идентификатору
	 *
	 * @param id идентификатор Клиента
	 * @return найденный Клиент
	 * @throws ClientNotFoundException если Клиент не найден
	 */
	@Override
	public ClientDto getById(Long id) throws Exception {
		Client client = getClientById(id);
		return clientMapper.clientToClientDto(client);
	}

	/**
	 * Удаляет существующиего Клиента
	 *
	 * @param id идентификатор Клиента для удаления
	 * @throws ClientNotFoundException если Клиент не найден
	 */
	@Override
	public void delete(Long id) throws Exception {
		Client client = getClientById(id);
		clientRepository.delete(client.getId());
		log.info("Client with id= '{}' delete", id);
	}

	/**
	 * Обновляет уже существующиего Клиента из информации полученной в DTO
	 *
	 * @param id     идентификатор Клиента для обновления
	 * @param client DTO с информацией об обновлении
	 * @throws ClientNotFoundException если Клиент не найден
	 */
	@Override
	public void update(Long id, ClientDto client) throws Exception {
		Client clientFromDB = getClientById(id);
		log.info("Обновление КЛИЕНТА с id= '{}'", id);
		clientRepository.update(clientFromDB.getId(), client);
	}

	/**
	 * Ищет Клиента по идентификатору
	 *
	 * @param id идентификатор Клиента
	 * @return найденный Клиент
	 * @throws ClientNotFoundException если Клиент не найден
	 */
	private Client getClientById(Long id) throws Exception {
		Client client = clientRepository.getById(id);
		if (client.getId() == null) {
			throw new ClientNotFoundException(id);
		} else {
			return client;
		}
	}

}
