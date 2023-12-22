package ru.clevertec.servlet.task.repository;

import ru.clevertec.servlet.task.entity.model.Client;

import java.util.List;

public interface ClientSortingRepository {

	List<Client> sortingClients(int pagesize, int page, String sorting) throws Exception;

}
