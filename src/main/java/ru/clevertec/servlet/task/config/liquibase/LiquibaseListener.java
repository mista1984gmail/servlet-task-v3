package ru.clevertec.servlet.task.config.liquibase;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import lombok.SneakyThrows;
import ru.clevertec.servlet.task.config.LoadProperties;
import ru.clevertec.servlet.task.context.ApplicationContext;
import ru.clevertec.servlet.task.database.connection.DBConnectionProvider;
import ru.clevertec.servlet.task.database.postgresql.connection.JDBCPostgreSQLConnection;
import ru.clevertec.servlet.task.database.postgresql.service.BootstrapDataBasePostgreSQL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;

@WebListener
public class LiquibaseListener implements ServletContextListener {

	private final static String LIQUIBASE_ENABLED = "liquibase_enabled";
	private final static String CHANGE_LOG_FILE = "db/changelog/db.changelog-master.xml";


	/**
	 * Инициализирует ApplicationContext, то есть создает все объекты
	 * с соответсвующими зависимостями.
	 * Запускает Liquibase.
	 * При первом запуске заполняет таблицу с Клиентами,
	 * количество Клиентов можно задать с помощью константы
	 * DEFAULT_NUMBER_OF_CLIENTS_CREATED (по дефолту 50).
	 *
	 */
	@SneakyThrows
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext.initBeanFactory();
		if (Boolean.parseBoolean(LoadProperties.getProperties()
											 .getProperty(LIQUIBASE_ENABLED))) {
			final DBConnectionProvider dbConnectionProvider = new JDBCPostgreSQLConnection();
			final Connection connection = dbConnectionProvider.getConnection();
			final Database database = DatabaseFactory.getInstance()
													 .findCorrectDatabaseImplementation(new JdbcConnection(connection));
			final Liquibase liquibase = new Liquibase(CHANGE_LOG_FILE, new CompositeResourceAccessor(new ClassLoaderResourceAccessor(), new FileSystemResourceAccessor()), database);
			liquibase.update(new Contexts(), new LabelExpression());

			BootstrapDataBasePostgreSQL bootstrapDataBasePostgreSQL = new BootstrapDataBasePostgreSQL();
			bootstrapDataBasePostgreSQL.fillDataBase();}
		}
	}
