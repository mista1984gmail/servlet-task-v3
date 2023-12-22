package ru.clevertec.servlet.task.cache.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.servlet.task.cache.Cache;
import ru.clevertec.servlet.task.entity.model.Client;
import ru.clevertec.servlet.task.util.ClientTestData;
import ru.clevertec.servlet.task.util.ConstantsForTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class LFUCacheTest {

	private Cache lfuCache;

	@BeforeEach
	void setUp() {
		lfuCache = new LFUCache();
		lfuCache.setSizeCache(ConstantsForTest.CACHE_SIZE);
	}

	@Test
	void shouldReturnObjectById() {
		// given
		Client client = ClientTestData.builder()
									  .build()
									  .buildClient();
		Long id = ConstantsForTest.ID;
		Client savedProduct = (Client) lfuCache.save(id, client);

		//when
		Client actual = (Client) lfuCache.getById(id);

		//then
		assertThat(actual)
				.hasFieldOrPropertyWithValue(Client.Fields.firstName, savedProduct.getFirstName())
				.hasFieldOrPropertyWithValue(Client.Fields.lastName, savedProduct.getLastName())
				.hasFieldOrPropertyWithValue(Client.Fields.email, savedProduct.getEmail())
				.hasFieldOrPropertyWithValue(Client.Fields.telephone, savedProduct.getTelephone())
				.hasFieldOrPropertyWithValue(Client.Fields.birthday, savedProduct.getBirthday())
				.hasFieldOrPropertyWithValue(Client.Fields.registrationDate, savedProduct.getRegistrationDate());
	}

	@Test
	void shouldReturnNull_whenDoNotFindObjectById() {
		// given
		Long id = ConstantsForTest.ID;

		//when
		Client actual = (Client) lfuCache.getById(id);

		//then
		assertNull(actual);
	}

	@Test
	void shouldSaveObject() {
		// given
		Client client = ClientTestData.builder()
									  .build()
									  .buildClient();
		Long id = ConstantsForTest.ID;

		//when
		Client expected = (Client) lfuCache.save(id, client);
		Client actual = (Client) lfuCache.getById(id);

		//then
		assertThat(actual)
				.hasFieldOrPropertyWithValue(Client.Fields.firstName, expected.getFirstName())
				.hasFieldOrPropertyWithValue(Client.Fields.lastName, expected.getLastName())
				.hasFieldOrPropertyWithValue(Client.Fields.email, expected.getEmail())
				.hasFieldOrPropertyWithValue(Client.Fields.telephone, expected.getTelephone())
				.hasFieldOrPropertyWithValue(Client.Fields.birthday, expected.getBirthday())
				.hasFieldOrPropertyWithValue(Client.Fields.registrationDate, expected.getRegistrationDate());
	}


	@Test
	void shouldDeleteObjectById() {
		// given
		Client client = ClientTestData.builder()
									  .build()
									  .buildClient();
		Long id = ConstantsForTest.ID;
		Client savedClient = (Client) lfuCache.save(id, client);

		//when
		lfuCache.delete(id);
		Client actual = (Client) lfuCache.getById(id);

		//then
		assertNull(actual);
	}

	@Test
	void shouldDeleteFromCacheRuleFIFO() {
		// given
		Client firstClient = ClientTestData.builder()
										   .withId(1l)
										   .build()
										   .buildClient();
		Long firstId = 1L;
		Client secondClient = ClientTestData.builder()
											.withId(2l)
											.build()
											.buildClient();
		Long secondId = 2L;
		Client thirdClient = ClientTestData.builder()
										   .withId(3l)
										   .build()
										   .buildClient();
		Long thirdId = 3L;
		Client fourthClient = ClientTestData.builder()
											.withId(4l)
											.build()
											.buildClient();
		Long fourthId = 4L;

		Client firstObject = (Client) lfuCache.save(firstId, firstClient);
		Client secondObject = (Client) lfuCache.save(secondId, secondClient);
		Client thirdObject = (Client) lfuCache.save(thirdId, thirdClient);
		Client fourthObject = (Client) lfuCache.save(fourthId, fourthClient);

		//when
		Client actual = (Client) lfuCache.getById(thirdId);

		//then
		assertNull(actual);
	}

}