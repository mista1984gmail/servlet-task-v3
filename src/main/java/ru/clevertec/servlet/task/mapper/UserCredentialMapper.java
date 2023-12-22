package ru.clevertec.servlet.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.servlet.task.security.entity.UserCredential;
import ru.clevertec.servlet.task.security.entity.dto.UserCredentialDto;

@Mapper
public interface UserCredentialMapper {

	/**
	 * Маппит DTO в Пользователя без id
	 *
	 * @param userCredentialDto - DTO для маппинга
	 * @return новый Пользователь
	 */
	@Mapping(ignore = true, target = "id")
	UserCredential userDtoToUser(UserCredentialDto userCredentialDto);

	/**
	 * Маппит Пользователя в DTO без id
	 *
	 * @param userCredential - Пользователь для маппинга
	 * @return новый DTO
	 */
	UserCredentialDto userToUserDto(UserCredential userCredential);

}
