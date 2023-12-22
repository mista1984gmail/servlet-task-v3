package ru.clevertec.servlet.task.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;
import ru.clevertec.servlet.task.exception.ClientNotFoundException;
import ru.clevertec.servlet.task.mapper.ClientMapper;
import ru.clevertec.servlet.task.repository.ClientRepository;
import ru.clevertec.servlet.task.util.ClientTestData;
import ru.clevertec.servlet.task.util.ConstantsForTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

	@Mock
	private ClientRepository clientRepository;
	@Mock
	private ClientMapper clientMapper;
	@Captor
	private ArgumentCaptor<Client> clientCaptor;
	@InjectMocks
	private ClientServiceImpl clientService;

	@Test
	void shouldDeleteClient() throws Exception {
		// given
		Long id = ConstantsForTest.ID;
		Client clientFromDB = ClientTestData.builder()
											.build()
											.buildClient();

		when(clientRepository.getById(id))
				.thenReturn(clientFromDB);
		doNothing()
				.when(clientRepository)
				.delete(id);

		//when
		clientService.delete(id);

		//then
		verify(clientRepository).delete(id);
	}

	@Test
	void shouldNotDeleteClientAndThrowsClientNotFoundException() throws Exception {
		// given
		Long id = ConstantsForTest.ID;
		String errorMessage = "Client with id: " + id + " not found";
		Client client = ClientTestData.builder()
									  .withId(null)
									  .build()
									  .buildClient();

		when(clientRepository.getById(id))
				.thenReturn(client);

		//when
		ClientNotFoundException thrown = assertThrows(ClientNotFoundException.class, () -> {
			clientService.delete(id);
		});

		//then
		Assertions.assertEquals(errorMessage, thrown.getMessage());
		verify(clientRepository, never()).delete(id);
	}

	@Test
	void shouldNotGetClientByIdAndThrowsClientNotFoundException() throws Exception {
		// given
		Long id = ConstantsForTest.ID;
		String errorMessage = "Client with id: " + id + " not found";
		Client client = ClientTestData.builder()
									  .withId(null)
									  .build()
									  .buildClient();

		when(clientRepository.getById(id))
				.thenReturn(client);

		//when
		ClientNotFoundException thrown = assertThrows(ClientNotFoundException.class, () -> {
			clientService.getById(id);
		});

		//then
		Assertions.assertEquals(errorMessage, thrown.getMessage());
	}

	@Test
	void shouldGetClientById() throws Exception {
		// given
		Long id = ConstantsForTest.ID;
		Client client = ClientTestData.builder()
									  .build()
									  .buildClient();
		ClientDto expected = ClientTestData.builder()
										   .build()
										   .buildClientDto();

		when(clientRepository.getById(id))
				.thenReturn(client);
		when(clientMapper.clientToClientDto(client))
				.thenReturn(expected);

		//when
		ClientDto actual = clientService.getById(id);

		//then
		assertEquals(expected, actual);
		verify(clientRepository).getById(id);
		verify(clientMapper).clientToClientDto(client);
	}

	@Test
	void shouldCreateClient() throws Exception {
		// given
		ClientDto clientDtoForSave = ClientTestData.builder()
												   .build()
												   .buildClientDto();
		Client clientForSave = ClientTestData.builder()
											 .build()
											 .buildClient();
		Long expectedId = ConstantsForTest.ID;

		when(clientMapper.clientDtoToClient(clientDtoForSave))
				.thenReturn(clientForSave);
		when(clientRepository.save(clientForSave))
				.thenReturn(clientForSave);

		//when
		Long actualId = clientService.create(clientDtoForSave);

		//then
		verify(clientRepository).save(clientCaptor.capture());
		verify(clientMapper).clientDtoToClient(clientDtoForSave);
		Client actual = clientCaptor.getValue();

		assertThat(actual)
				.hasFieldOrPropertyWithValue(Client.Fields.id, clientForSave.getId())
				.hasFieldOrPropertyWithValue(Client.Fields.firstName, clientForSave.getFirstName())
				.hasFieldOrPropertyWithValue(Client.Fields.lastName, clientForSave.getLastName())
				.hasFieldOrPropertyWithValue(Client.Fields.email, clientForSave.getEmail())
				.hasFieldOrPropertyWithValue(Client.Fields.telephone, clientForSave.getTelephone())
				.hasFieldOrPropertyWithValue(Client.Fields.birthday, clientForSave.getBirthday())
				.hasFieldOrPropertyWithValue(Client.Fields.registrationDate, clientForSave.getRegistrationDate());

		assertEquals(expectedId, actualId);
	}

	@Test
	void shouldUpdateClient() throws Exception {
		// given
		ClientDto clientDtoForUpdate = ClientTestData.builder()
													 .withFirstName("Prtia")
													 .withLastName("Sergeev")
													 .build()
													 .buildClientDto();
		Client clientFromDB = ClientTestData.builder()
											.build()
											.buildClient();
		Client expected = ClientTestData.builder()
										.withFirstName("Prtia")
										.withLastName("Sergeev")
										.build()
										.buildClient();
		Long id = ConstantsForTest.ID;

		when(clientRepository.getById(id)).thenReturn(clientFromDB);
		doNothing().when(clientRepository)
				   .update(id, clientDtoForUpdate);

		//when
		clientService.update(id, clientDtoForUpdate);

		//then
		verify(clientRepository).update(id, clientDtoForUpdate);

		assertThat(clientDtoForUpdate)
				.hasFieldOrPropertyWithValue(Client.Fields.firstName, expected.getFirstName())
				.hasFieldOrPropertyWithValue(Client.Fields.lastName, expected.getLastName())
				.hasFieldOrPropertyWithValue(Client.Fields.email, expected.getEmail())
				.hasFieldOrPropertyWithValue(Client.Fields.telephone, expected.getTelephone())
				.hasFieldOrPropertyWithValue(Client.Fields.birthday, expected.getBirthday())
				.hasFieldOrPropertyWithValue(Client.Fields.registrationDate, expected.getRegistrationDate());
	}

	@Test
	void shouldNotUpdateClientAndThrowsClientNotFoundException() throws Exception {
		// given
		Long id = ConstantsForTest.ID;
		ClientDto clientDtoForUpdate = ClientTestData.builder()
													 .withFirstName("Prtia")
													 .withLastName("Sergeev")
													 .build()
													 .buildClientDto();
		String errorMessage = "Client with id: " + id + " not found";
		Client client = ClientTestData.builder()
									  .withId(null)
									  .build()
									  .buildClient();

		when(clientRepository.getById(id))
				.thenReturn(client);

		//when
		ClientNotFoundException thrown = assertThrows(ClientNotFoundException.class, () -> {
			clientService.update(id, clientDtoForUpdate);
		});

		//then
		Assertions.assertEquals(errorMessage, thrown.getMessage());
	}

	@Test
	void shouldGetAllClients() throws Exception {
		// given
		Client firstClient = ClientTestData.builder()
										   .build()
										   .buildClient();
		Client secondClient = ClientTestData.builder()
											.withId(2L)
											.build()
											.buildClient();
		List<Client> products = Arrays.asList(firstClient, secondClient);
		ClientDto firstClientDto = ClientTestData.builder()
												 .build()
												 .buildClientDto();
		ClientDto secondClientDto = ClientTestData.builder()
												  .withId(2L)
												  .build()
												  .buildClientDto();
		List<ClientDto> expected = Arrays.asList(firstClientDto, secondClientDto);

		when(clientRepository.getAll())
				.thenReturn(products);
		when(clientMapper.clientToClientDto(firstClient))
				.thenReturn(firstClientDto);
		when(clientMapper.clientToClientDto(secondClient))
				.thenReturn(secondClientDto);

		//when
		List<ClientDto> actual = clientService.getAll();

		//then
		assertArrayEquals(expected.toArray(), actual.toArray());
		assertEquals(2, actual.size());
		verify(clientRepository).getAll();
		verify(clientMapper, times(2)).clientToClientDto(any());
	}

}