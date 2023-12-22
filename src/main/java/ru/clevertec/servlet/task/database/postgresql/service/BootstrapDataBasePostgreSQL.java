package ru.clevertec.servlet.task.database.postgresql.service;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.servlet.task.context.ApplicationContext;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;
import ru.clevertec.servlet.task.mapper.ClientMapper;
import ru.clevertec.servlet.task.repository.ClientRepository;
import ru.clevertec.servlet.task.util.Constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Slf4j
public class BootstrapDataBasePostgreSQL {

    private final ClientRepository clientRepository = (ClientRepository) ApplicationContext.getBean(ClientRepository.class);
    private final  ClientMapper clientMapper = (ClientMapper) ApplicationContext.getBean(ClientMapper.class);

    public void fillDataBase() throws Exception {
        List<Client> clients = clientRepository.getAll();
        Faker faker = new Faker();
        if (clients.size() == 0) {
            for (int i = 0; i < Constants.DEFAULT_NUMBER_OF_CLIENTS_CREATED; i++) {
                ClientDto clientDto = new ClientDto();
                String firstName = faker.name()
                                        .firstName()
                                        .replaceAll("\'", "");
                String lastName = faker.name()
                                       .lastName()
                                       .replaceAll("\'", "");
                clientDto.setFirstName(firstName);
                clientDto.setLastName(lastName);
                clientDto.setEmail(firstName.toLowerCase() + "_" + lastName.toLowerCase() + "_" + i + "@gmail.com");
                clientDto.setTelephone(createTelephoneNumber(faker.number()
                                                                  .numberBetween(1, 9999999)));
                clientDto.setBirthday(LocalDate.of(1960, Month.JANUARY, 1)
                                               .plusDays(faker.number()
                                                              .numberBetween(1, 16000)));
                clientDto.setRegistrationDate(LocalDateTime.of(2015, 1, 1, 15, 00)
                                                           .plusMinutes(faker.number()
                                                                             .numberBetween(1, 3153600)));
                clientRepository.save(clientMapper.clientDtoToClient(clientDto));
            }
            log.info("Table with clients is full");
        }
    }

    private String createTelephoneNumber(int i) {
        String str = String.valueOf(i);
        String phoneNumber = "+375290000000";
        return phoneNumber.substring(0, phoneNumber.length() - str.length()) + i;
    }

}
