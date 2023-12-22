package ru.clevertec.servlet.task.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConstantsForTest {

	public final static Long ID = 1L;
	public final static String CLIENT_FIRST_NAME = "Ivan";
	public final static String CLIENT_SECOND_NAME = "Ivanov";
	public final static String CLIENT_EMAIL = "ivan2000@mail.ru";
	public final static String CLIENT_TELEPHONE = "+375291234567";
	public final static LocalDate CLIENT_BIRTHDAY = LocalDate.of(2000, 1, 1);
	public final static LocalDateTime CLIENT_REGISTRATION_DATE = LocalDateTime.of(2023, 11, 17, 15, 00);
	public final static String CLIENT_PASSWORD = "123456";
	public final static Integer CACHE_SIZE = 3;
	public static final String PATH_PDF_CLIENT = "src/test/pdf/reports/info_about_client";
	public static final String FILE_NAME_PDF_CLIENT = "IvanIvanov_info";
	public static final String EXPECTED_PDF_TEXT_CLIENT = "Info about client: Ivan Ivanov\n" +
															" \n" +
															"Age: 23\n" +
															"Email: ivan2000@mail.ru\n" +
															"Telephone: +375291234567\n" +
															"Registration date: 17.11.2023 15:00:00";

	public static final String EXPECTED_PDF_TEXT_CLIENTS = "List of clients\n" +
												" \n" +
												"# First name Last name Email Telephone Date of Registration\n" +
												"birth date\n" +
												"1 Ivan Ivanov ivan2000@mail.ru +375291234567 01.01.2000 17.11.2023 15:00:00\n" +
												"2 Petia Petrov petia2000@mail.ru +375291234568 15.08.2003 13.10.2023 15:00:00\n" +
												"3 Sergey Sergeev sergey2000@mail.ru +375291234569 11.06.1998 11.05.2023 18:00:00";
	public static final String PATH_PDF_CLIENTS = "src/test/pdf/reports/info_about_clients";
	public static final String FILE_NAME_PDF_CLIENTS = "ListOfClients";
}
