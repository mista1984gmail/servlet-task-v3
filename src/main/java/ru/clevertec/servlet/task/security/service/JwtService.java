package ru.clevertec.servlet.task.security.service;

import ru.clevertec.servlet.task.security.entity.UUIDToken;
import ru.clevertec.servlet.task.security.repository.UUIDTokenRepository;

import java.util.UUID;

public class JwtService {

	private final UUIDTokenRepository uuidTokenRepository;

	public JwtService(UUIDTokenRepository uuidTokenRepository) {
		this.uuidTokenRepository = uuidTokenRepository;
	}

	/**
	 * Генерирует token в виде UUID
	 *
	 * @param id - часть token
	 * @return token
	 */
	private String generateToken(Long id) {
		return UUID.randomUUID() + "-" + id;
	}

	/**
	 * Создает объект UUIDToken
	 * и сохраняет его в базу данных.
	 * Возвращает token.
	 *
	 * @throws RuntimeException - если сохранение token не прошло успешно
	 * @param id - часть token
	 * @return token
	 */
	public String getToken(Long id) {
		String token = generateToken(id);
		UUIDToken uuidToken = new UUIDToken();
		uuidToken.setUserId(id);
		uuidToken.setToken(token);
		try {
			uuidTokenRepository.save(uuidToken);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return token;
	}

	/**
	 * Проводит валидацию token
	 *
	 * @throws RuntimeException - если не получен token из базы данных
	 * @param token - token для валидации
	 * @return true - если валидация token прошла успешно,
	 * то есть предоставленный token совпадает с token из базы данных,
	 * в противном случае - false
	 */
	public boolean validateToken(String token) {
		Long userId = getIdFromToken(token);
		String uuidToken = null;
		try {
			uuidToken = uuidTokenRepository.getToken(userId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return token.equals(uuidToken);
	}

	/**
	 * Удаляет все token из базы данных
	 *
	 */
	public void deleteTokens() {
		uuidTokenRepository.delete();
	}

	/**
	 * Получает id Пользователя из token
	 *
	 * @param token для извлечения id
	 * @return id Пользователя
	 */
	private Long getIdFromToken(String token) {
		int index = token.lastIndexOf("-");
		return Long.parseLong(token.substring(index + 1));
	}

}
