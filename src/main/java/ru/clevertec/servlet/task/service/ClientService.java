package ru.clevertec.servlet.task.service;

import ru.clevertec.servlet.task.entity.dto.ClientDto;

import java.util.List;

public interface ClientService {

    Long create(ClientDto client) throws Exception;

    List<ClientDto> getAll() throws Exception;

    List<ClientDto> getClientsWithPagination(int pagesize, int page, String sorting) throws Exception;

    ClientDto getById(Long id) throws Exception;

    void delete(Long id) throws Exception;

    void update(Long id, ClientDto client) throws Exception;

}
