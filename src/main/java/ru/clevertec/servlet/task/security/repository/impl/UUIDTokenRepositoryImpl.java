package ru.clevertec.servlet.task.security.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.servlet.task.database.postgresql.connection.DataSource;
import ru.clevertec.servlet.task.security.entity.UUIDToken;
import ru.clevertec.servlet.task.security.repository.UUIDTokenRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class UUIDTokenRepositoryImpl implements UUIDTokenRepository {

	@Override
	public boolean save(UUIDToken uuidToken) throws Exception {
		try (Connection connection = DataSource.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into uuid_token (user_id, token) values(?,?)"
			);
			preparedStatement.setLong(1, uuidToken.getUserId());
			preparedStatement.setString(2, uuidToken.getToken());
			int status = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public String getToken(Long id) throws Exception {
		UUIDToken uuidToken = new UUIDToken();
		try (Connection connection = DataSource.getConnection()){
			String selectTableSQL = "SELECT * from uuid_token where user_id = " + id;
			PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Long uuidTokenId = Long.parseLong(rs.getString("id"));
				Long userId = rs.getLong("user_id");
				String token = rs.getString("token");
				uuidToken.setId(uuidTokenId);
				uuidToken.setUserId(userId);
				uuidToken.setToken(token);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return uuidToken.getToken();
	}

	@Override
	public void delete() {
		String deleteTableSQL = "DELETE FROM uuid_token WHERE id > 0";
		executeStatement(deleteTableSQL);
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
