package ru.clevertec.servlet.task.security.repository;

import ru.clevertec.servlet.task.security.entity.UUIDToken;

public interface UUIDTokenRepository {

	boolean save(UUIDToken uuidToken) throws Exception;

	String getToken(Long id) throws Exception;

	void delete();
}
