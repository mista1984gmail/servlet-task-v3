package ru.clevertec.servlet.task.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.servlet.task.database.postgresql.connection.DataSource;
import ru.clevertec.servlet.task.entity.model.Client;
import ru.clevertec.servlet.task.repository.ClientSortingRepository;
import ru.clevertec.servlet.task.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientSortingRepositoryImpl implements ClientSortingRepository {

	private final static DateTimeFormatter FORMATTER_FOR_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final static DateTimeFormatter FORMATTER_FOR_LOCAL_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public List<Client> sortingClients(int pagesize, int page, String sorting) throws Exception {
		if (pagesize == 0) {
			pagesize = Constants.DEFAULT_PAGE_SIZE;
		}
		int offset = ((page - 1) * pagesize);
		String sort = "c.first_name";
		if (sorting.equals("birthday")) {
			sort = "c.birthday desc";
		}
		if (sorting.equals("first_name")) {
			sort = "c.first_name";
		}
		if (sorting.equals("registration_date")) {
			sort = "c.registration_date desc";
		}
		String selectTableSQL = "select * from clients as c order by " + sort + " limit " + pagesize + " offset " + offset;
		return getClients(selectTableSQL);
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

}
