package ru.clevertec.servlet.task.repository;


import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;

import java.util.List;

public interface ClientRepository {

    Client save(Client client) throws Exception;

    List<Client> getAll() throws Exception;


    Client getById(Long id) throws Exception;


    void delete(Long id) throws Exception;


    void update(Long id, ClientDto client);

    List<Client> getClientsWithPagination(int pagesize, int page) throws Exception;
}
