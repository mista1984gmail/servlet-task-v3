package ru.clevertec.servlet.task.security.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.servlet.task.database.postgresql.connection.DataSource;
import ru.clevertec.servlet.task.security.entity.UserCredential;
import ru.clevertec.servlet.task.security.repository.CredentialRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class CredentialRepositoryImpl implements CredentialRepository {

	@Override
	public boolean save(UserCredential userCredential) throws Exception {
		try (Connection connection = DataSource.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into user_credential (email, password) values(?,?)"
			);
			preparedStatement.setString(1, userCredential.getEmail());
			preparedStatement.setString(2, userCredential.getPassword());
			int status = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public UserCredential getByEmail(String email) throws Exception {
		UserCredential userCredential = new UserCredential();
		try (Connection connection = DataSource.getConnection()){
			String selectTableSQL = "SELECT * from user_credential where email = '" + email + "'";
			PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Long userCredentialId = Long.parseLong(rs.getString("id"));
				String userCredentialEmail = rs.getString("email");
				String password = rs.getString("password");
				userCredential.setId(userCredentialId);
				userCredential.setEmail(userCredentialEmail);
				userCredential.setPassword(password);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return userCredential;
	}
}
