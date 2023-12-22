package ru.clevertec.servlet.task.context;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.clevertec.json.parser.JSONParser;
import ru.clevertec.json.parser.impl.JSONParserImpl;
import ru.clevertec.servlet.task.mapper.ClientMapper;
import ru.clevertec.servlet.task.mapper.ClientMapperImpl;
import ru.clevertec.servlet.task.mapper.UserCredentialMapper;
import ru.clevertec.servlet.task.mapper.UserCredentialMapperImpl;
import ru.clevertec.servlet.task.pdfcreator.PDFWriter;
import ru.clevertec.servlet.task.pdfcreator.impl.PDFWriterImpl;
import ru.clevertec.servlet.task.repository.ClientRepository;
import ru.clevertec.servlet.task.repository.ClientSortingRepository;
import ru.clevertec.servlet.task.repository.impl.ClientRepositoryImpl;
import ru.clevertec.servlet.task.repository.impl.ClientSortingRepositoryImpl;
import ru.clevertec.servlet.task.security.repository.CredentialRepository;
import ru.clevertec.servlet.task.security.repository.UUIDTokenRepository;
import ru.clevertec.servlet.task.security.repository.impl.CredentialRepositoryImpl;
import ru.clevertec.servlet.task.security.repository.impl.UUIDTokenRepositoryImpl;
import ru.clevertec.servlet.task.security.service.AuthService;
import ru.clevertec.servlet.task.security.service.JwtService;
import ru.clevertec.servlet.task.service.ClientService;
import ru.clevertec.servlet.task.service.impl.ClientServiceImpl;
import ru.clevertec.servlet.task.validator.ClientValidator;
import ru.clevertec.servlet.task.validator.UserValidator;
import ru.clevertec.servlet.task.validator.impl.ClientValidatorImpl;
import ru.clevertec.servlet.task.validator.impl.UserValidatorImpl;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

	private final static JSONParser jsonParser = new JSONParserImpl();
	private final static ClientRepository clientRepository = new ClientRepositoryImpl();
	private final static ClientMapper clientMapper = new ClientMapperImpl();
	private final static ClientSortingRepository clientSortingRepository = new ClientSortingRepositoryImpl();
	private final static ClientService clientService = new ClientServiceImpl(clientRepository, clientSortingRepository, clientMapper);
	private final static PDFWriter pdfWriter = new PDFWriterImpl();
	private final static ClientValidator clientValidator = new ClientValidatorImpl();
	private final static UUIDTokenRepository uuidTokenRepository = new UUIDTokenRepositoryImpl();
	public final static JwtService jwtService = new JwtService(uuidTokenRepository);
	private final static CredentialRepository credentialRepository = new CredentialRepositoryImpl();
	private final static UserCredentialMapper userCredentialMapper = new UserCredentialMapperImpl();
	private final static AuthService authService = new AuthService(credentialRepository, jwtService, userCredentialMapper);
	private final static UserValidator userValidator = new UserValidatorImpl();
	private final static XmlMapper xmlMapper = new XmlMapper();

	static {
		xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		xmlMapper.findAndRegisterModules();
		xmlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	private final static Map<Class, Object> beanFactory = new HashMap<>();

	/**
	 * Помещает объекты в маппу beanFactory.
	 * Ключ - класс объекта, значение - сам объект.
	 *
	 */
	public static void initBeanFactory(){
		if(beanFactory.isEmpty()){
			beanFactory.put(JSONParser.class, jsonParser);
			beanFactory.put(ClientRepository.class, clientRepository);
			beanFactory.put(ClientMapper.class, clientMapper);
			beanFactory.put(ClientService.class, clientService);
			beanFactory.put(PDFWriter.class, pdfWriter);
			beanFactory.put(ClientValidator.class, clientValidator);
			beanFactory.put(AuthService.class, authService);
			beanFactory.put(UserValidator.class, userValidator);
			beanFactory.put(JwtService.class, jwtService);
			beanFactory.put(CredentialRepository.class, credentialRepository);
			beanFactory.put(UserCredentialMapper.class, userCredentialMapper);
			beanFactory.put(UUIDTokenRepository.class, uuidTokenRepository);
			beanFactory.put(XmlMapper.class, xmlMapper);
		}
	}

	/**
	 * Получает объект из beanFactory по ключу - классу объекта.
	 *
	 * @param clazz - класс объкта, который надо получить из beanFactory.
	 */
	public static Object getBean(Class clazz){
		return beanFactory.get(clazz);
	}

}
