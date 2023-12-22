package ru.clevertec.servlet.task.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.servlet.task.aspect.annotation.DeleteClient;
import ru.clevertec.servlet.task.aspect.annotation.GetClient;
import ru.clevertec.servlet.task.aspect.annotation.SaveClient;
import ru.clevertec.servlet.task.aspect.annotation.UpdateClient;
import ru.clevertec.servlet.task.database.postgresql.connection.DataSource;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.entity.model.Client;
import ru.clevertec.servlet.task.repository.ClientRepository;
import ru.clevertec.servlet.task.util.Constants;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientRepositoryImpl implements ClientRepository {

    private final static DateTimeFormatter FORMATTER_FOR_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter FORMATTER_FOR_LOCAL_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    @SaveClient
    public Client save(Client client) throws ClassNotFoundException {
        Integer clientId;
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into clients(first_name, last_name, email, telephone, birthday, registration_date) values(?,?,?,?,?,?) RETURNING id"
            );
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setDate(5, Date.valueOf(client.getBirthday()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(client.getRegistrationDate()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                clientId = Integer.parseInt(rs.getString("id"));
                client.setId(clientId.longValue());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    @Override
    public List<Client> getAll() throws Exception {
        String selectAllClients = "SELECT * from clients";
        return getClients(selectAllClients);
    }

    @Override
    @GetClient
    public Client getById(Long id) throws Exception {
        log.info("Get client from DB");
        Client client = new Client();
        try (Connection connection = DataSource.getConnection()){
            String selectTableSQL = "SELECT * from clients where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long clientId = Long.parseLong(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String birthdayString = rs.getString("birthday");
                LocalDate birthday = LocalDate.parse(birthdayString, FORMATTER_FOR_LOCAL_DATE);
                String registrationTimeString = rs.getString("registration_date");
                LocalDateTime registrationDate = LocalDateTime.parse(registrationTimeString, FORMATTER_FOR_LOCAL_DATE_TIME);
                client.setId(clientId);
                client.setFirstName(firstName);
                client.setLastName(lastName);
                client.setEmail(email);
                client.setTelephone(telephone);
                client.setBirthday(birthday);
                client.setRegistrationDate(registrationDate);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return client;
    }

    @Override
    @DeleteClient
    public void delete(Long id) {
        String deleteTableSQL = "DELETE FROM clients WHERE id = " + id;
        executeStatement(deleteTableSQL);
    }

    @Override
    @UpdateClient
    public void update(Long id, ClientDto client) {
        String updateClientSQL = "UPDATE clients SET "
                + "first_name =" + "'" + client.getFirstName() + "',"
                + "last_name =" + "'" + client.getLastName() + "',"
                + "email =" + "'" + client.getEmail() + "',"
                + "telephone =" + "'" + client.getTelephone() + "',"
                + "birthday =" + "'" + client.getBirthday() + "',"
                + "registration_date =" + "'" + client.getRegistrationDate() + "'"
                + "WHERE id = " + id;
        executeStatement(updateClientSQL);
    }

    @Override
    public List<Client> getClientsWithPagination(int pagesize, int page) throws Exception {
        if (pagesize == 0) {
            pagesize = Constants.DEFAULT_PAGE_SIZE;
        }
        int offset = ((page - 1) * pagesize);
        String getAllClientsWithPagination = "select * from clients limit " + pagesize + " offset " + offset;
        return getClients(getAllClientsWithPagination);
    }

    private List<Client> getClients(String sql) throws Exception {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long clientId = Long.parseLong(rs.getString("id"));
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String birthdayString = rs.getString("birthday");
                LocalDate birthday = LocalDate.parse(birthdayString, FORMATTER_FOR_LOCAL_DATE);
                String registrationTimeString = rs.getString("registration_date");
                LocalDateTime registrationDate = LocalDateTime.parse(registrationTimeString, FORMATTER_FOR_LOCAL_DATE_TIME);
                Client client = new Client(clientId, firstName, lastName, email, telephone, birthday, registrationDate);
                clients.add(client);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return clients;
    }

    public void executeStatement(String sql) {
        try (Connection connection = DataSource.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

}