package ru.clevertec.servlet.task.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnectionProvider {
	Connection getConnection() throws SQLException, ClassNotFoundException;

}
