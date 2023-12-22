package ru.clevertec.servlet.task.util;

import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClientsTestData {

	public static List<ClientDto> getListOfClientsDto(){
		ClientDto clientOne = new ClientDto("Ivan", "Ivanov", "ivan2000@mail.ru", "+375291234567", LocalDate.of(2000, 1, 1), LocalDateTime.of(2023, 11, 17, 15, 00));
		ClientDto clientSecond = new ClientDto("Petia", "Petrov", "petia2000@mail.ru", "+375291234568", LocalDate.of(2003, 8, 15), LocalDateTime.of(2023, 10, 13, 15, 00));
		ClientDto clientThird = new ClientDto("Sergey", "Sergeev", "sergey2000@mail.ru", "+375291234569", LocalDate.of(1998, 6, 11), LocalDateTime.of(2023, 5, 11, 18, 00));
		List<ClientDto> clients = new ArrayList<>();
		clients.add(clientOne);
		clients.add(clientSecond);
		clients.add(clientThird);
		return clients;
	}

	public static List<Client> getListOfClients(){
		Client clientOne = new Client(1L,"Ivan", "Ivanov", "ivan2000@mail.ru", "+375291234567", LocalDate.of(2000, 1, 1), LocalDateTime.of(2023, 11, 17, 15, 00));
		Client clientSecond = new Client(2L, "Petia", "Petrov", "petia2000@mail.ru", "+375291234568", LocalDate.of(2003, 8, 15), LocalDateTime.of(2023, 10, 13, 15, 00));
		Client clientThird = new Client(3L, "Sergey", "Sergeev", "sergey2000@mail.ru", "+375291234569", LocalDate.of(1998, 6, 11), LocalDateTime.of(2023, 5, 11, 18, 00));
		List<Client> clients = new ArrayList<>();
		clients.add(clientOne);
		clients.add(clientSecond);
		clients.add(clientThird);
		return clients;
	}

	public static List<ClientDto> getListOfClientsDtoSortingByAge(){
		ClientDto clientOne = new ClientDto("Ivan", "Ivanov", "ivan2000@mail.ru", "+375291234567", LocalDate.of(2000, 1, 1), LocalDateTime.of(2023, 11, 17, 15, 00));
		ClientDto clientSecond = new ClientDto("Petia", "Petrov", "petia2000@mail.ru", "+375291234568", LocalDate.of(2003, 8, 15), LocalDateTime.of(2023, 10, 13, 15, 00));
		ClientDto clientThird = new ClientDto("Sergey", "Sergeev", "sergey2000@mail.ru", "+375291234569", LocalDate.of(1998, 6, 11), LocalDateTime.of(2023, 5, 11, 18, 00));
		List<ClientDto> clients = new LinkedList<>();
		clients.add(clientSecond);
		clients.add(clientOne);
		clients.add(clientThird);
		return clients;
	}

	public static List<Client> getListOfClientsSortingByAge(){
		Client clientOne = new Client(1L,"Ivan", "Ivanov", "ivan2000@mail.ru", "+375291234567", LocalDate.of(2000, 1, 1), LocalDateTime.of(2023, 11, 17, 15, 00));
		Client clientSecond = new Client(2L, "Petia", "Petrov", "petia2000@mail.ru", "+375291234568", LocalDate.of(2003, 8, 15), LocalDateTime.of(2023, 10, 13, 15, 00));
		Client clientThird = new Client(3L, "Sergey", "Sergeev", "sergey2000@mail.ru", "+375291234569", LocalDate.of(1998, 6, 11), LocalDateTime.of(2023, 5, 11, 18, 00));
		List<Client> clients = new LinkedList<>();
		clients.add(clientSecond);
		clients.add(clientOne);
		clients.add(clientThird);
		return clients;
	}

	public static List<ClientDto> getListOfClientsDtoSortingByRegistrationDate(){
		ClientDto clientOne = new ClientDto("Ivan", "Ivanov", "ivan2000@mail.ru", "+375291234567", LocalDate.of(2000, 1, 1), LocalDateTime.of(2023, 11, 17, 15, 00));
		ClientDto clientSecond = new ClientDto("Petia", "Petrov", "petia2000@mail.ru", "+375291234568", LocalDate.of(2003, 8, 15), LocalDateTime.of(2023, 10, 13, 15, 00));
		ClientDto clientThird = new ClientDto("Sergey", "Sergeev", "sergey2000@mail.ru", "+375291234569", LocalDate.of(1998, 6, 11), LocalDateTime.of(2023, 5, 11, 18, 00));
		List<ClientDto> clients = new LinkedList<>();
		clients.add(clientSecond);
		clients.add(clientOne);
		clients.add(clientThird);
		return clients;
	}

	public static List<Client> getListOfClientsSortingByRegistrationDate(){
		Client clientOne = new Client(1L,"Ivan", "Ivanov", "ivan2000@mail.ru", "+375291234567", LocalDate.of(2000, 1, 1), LocalDateTime.of(2023, 11, 17, 15, 00));
		Client clientSecond = new Client(2L, "Petia", "Petrov", "petia2000@mail.ru", "+375291234568", LocalDate.of(2003, 8, 15), LocalDateTime.of(2023, 10, 13, 15, 00));
		Client clientThird = new Client(3L, "Sergey", "Sergeev", "sergey2000@mail.ru", "+375291234569", LocalDate.of(1998, 6, 11), LocalDateTime.of(2023, 5, 11, 18, 00));
		List<Client> clients = new LinkedList<>();
		clients.add(clientSecond);
		clients.add(clientOne);
		clients.add(clientThird);
		return clients;
	}

	public static List<ClientDto> getListOfClientsDtoWithAgeOlderThan25(){
		ClientDto clientThird = new ClientDto("Sergey", "Sergeev", "sergey2000@mail.ru", "+375291234569", LocalDate.of(1998, 6, 11), LocalDateTime.of(2023, 5, 11, 18, 00));
		List<ClientDto> clients = new LinkedList<>();
		clients.add(clientThird);
		return clients;
	}

	public static List<Client> getListOfClientsWithAgeOlderThan25(){
		Client clientThird = new Client(1L, "Sergey", "Sergeev", "sergey2000@mail.ru", "+375291234569", LocalDate.of(1998, 6, 11), LocalDateTime.of(2023, 5, 11, 18, 00));
		List<Client> clients = new LinkedList<>();
		clients.add(clientThird);
		return clients;
	}

}