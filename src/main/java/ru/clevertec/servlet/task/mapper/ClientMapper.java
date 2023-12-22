package ru.clevertec.servlet.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;

import java.util.List;

@Mapper
public interface ClientMapper {

    /**
     * Маппит DTO в Клиента без id
     *
     * @param clientDto - DTO для маппинга
     * @return новый Клиент
     */
    @Mapping(ignore = true, target = "id")
    Client clientDtoToClient(ClientDto clientDto);

    /**
     * Маппит Клиента в DTO без id
     *
     * @param client - Клиент для маппинга
     * @return новый DTO
     */
    ClientDto clientToClientDto(Client client);

    /**
     * Маппит список Клиентов в список DTO без id
     *
     * @param clients - Клиенты для маппинга
     * @return новый List<ClientDto>
     */
    List<ClientDto> clientsToClientsDto(List<Client> clients);

}