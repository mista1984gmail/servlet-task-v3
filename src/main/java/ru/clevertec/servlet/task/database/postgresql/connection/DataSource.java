package ru.clevertec.servlet.task.database.postgresql.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.clevertec.servlet.task.config.LoadProperties;
import ru.clevertec.servlet.task.util.Constants;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
	private final static String url = LoadProperties.getProperties().getProperty(Constants.URL);
	private final static String username = LoadProperties.getProperties().getProperty(Constants.USERNAME);
	private final static String password = LoadProperties.getProperties().getProperty(Constants.PASSWORD);
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		config.setJdbcUrl( url );
		config.setUsername( username );
		config.setPassword( password );
		config.addDataSourceProperty( "cachePrepStmts" , "true" );
		config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
		config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
		ds = new HikariDataSource( config );
	}

	private DataSource() {}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
