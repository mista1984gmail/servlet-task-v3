package ru.clevertec.servlet.task.database.postgresql.connection;

import ru.clevertec.servlet.task.config.LoadProperties;
import ru.clevertec.servlet.task.database.connection.DBConnectionProvider;
import ru.clevertec.servlet.task.util.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCPostgreSQLConnection implements DBConnectionProvider {
	private final static String databaseDriver = LoadProperties.getProperties().getProperty(Constants.DATABASE_DRIVER);
	private final static String url = LoadProperties.getProperties().getProperty(Constants.URL);
	private final static String username = LoadProperties.getProperties().getProperty(Constants.USERNAME);
	private final static String password = LoadProperties.getProperties().getProperty(Constants.PASSWORD);
	@Override
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(databaseDriver);
		return DriverManager.getConnection(url, username, password);
	}
}
