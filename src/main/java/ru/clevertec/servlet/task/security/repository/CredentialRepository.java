package ru.clevertec.servlet.task.security.repository;

import ru.clevertec.servlet.task.security.entity.UserCredential;

public interface CredentialRepository {

	boolean save(UserCredential userCredential) throws Exception;

	UserCredential getByEmail(String email) throws Exception;
}
