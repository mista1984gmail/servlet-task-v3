package ru.clevertec.servlet.task.security.service;

import ru.clevertec.servlet.task.exception.UserCredentialNotSavedException;
import ru.clevertec.servlet.task.mapper.UserCredentialMapper;
import ru.clevertec.servlet.task.security.entity.UserCredential;
import ru.clevertec.servlet.task.security.entity.dto.UserCredentialDto;
import ru.clevertec.servlet.task.security.repository.CredentialRepository;

public class AuthService {

	private final CredentialRepository credentialRepository;
	private final JwtService jwtService;
	private final UserCredentialMapper userCredentialMapper;

	public AuthService(CredentialRepository credentialRepository, JwtService jwtService, UserCredentialMapper userCredentialMapper) {
		this.credentialRepository = credentialRepository;
		this.jwtService = jwtService;
		this.userCredentialMapper = userCredentialMapper;
	}

	/**
	 * Создаёт нового Пользователя из DTO
	 *
	 * @throws UserCredentialNotSavedException - если Пользователь не сохранился
	 * @param credential DTO с информацией о создании
	 * @return true, если Пользователь сохранился, если не сохранился
	 * выбрасывается ошибка UserCredentialNotSavedException
	 */
	public boolean saveUser(UserCredentialDto credential) {
		boolean resultSaving;
		try {
			resultSaving = credentialRepository.save(userCredentialMapper.userDtoToUser(credential));
		} catch (Exception e) {
			throw new UserCredentialNotSavedException(credential.getEmail());
		}
		return resultSaving;
	}

	/**
	 * Проводит аутентификацию Пользователя. Если аутентификация прошла успешно
	 * возвращает токен, для доступа к другим ресурсам, если
	 * введены не корректные данные для аутентификации -
	 * выводит информацию об этом.
	 *
	 * @param credential DTO с информацией об аутентификации
	 * @return token - если аутентификация прошла успешно,
	 * в противном случае сообщение "Login or password is not correct!"
	 */
	public String authorization(UserCredentialDto credential) {
		UserCredential credentialForAuthorization;
		try {
			credentialForAuthorization = credentialRepository.getByEmail(credential.getEmail());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (credential.getPassword()
					  .equals(credentialForAuthorization.getPassword())) {
			return jwtService.getToken(credentialForAuthorization.getId());
		}
		return "Login or password is not correct!";
	}
}
