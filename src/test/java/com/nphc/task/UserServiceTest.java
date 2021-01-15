package com.nphc.task;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nphc.task.entity.User;
import com.nphc.task.repository.UserRepository;
import com.nphc.task.service.UserService;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService service;

	/**
	 * Create a mock implementation of the userRepository
	 */
	@MockBean
	private UserRepository repository;

	@Test
	@DisplayName("Test findById Success")
	void testFindById() {
		User employee1 = new User("e1001", "adam", "Gilly", 10.00, "12/01/2021");
		doReturn(Optional.of(employee1)).when(repository).findById("e1001");

		Optional<User> userOptional = service.getUserById("e1001");

		Assertions.assertTrue(userOptional.isPresent(), "User was not found");
		Assertions.assertSame(userOptional.get(), employee1, "The User returned was not the same as the mock");
	}

	@Test
	@DisplayName("Test findById Not Found")
	void testFindByIdNotFound() {
		doReturn(Optional.empty()).when(repository).findById("e1001");

		Optional<User> employee1 = service.getUserById("e100991");

		Assertions.assertFalse(employee1.isPresent(), "User should not be found");
	}

	@Test
	@DisplayName("Test save User")
	void testSave() {
		User employee1 = new User("e1001", "adam", "Gilly", 10.00, "12/01/2021");
		doReturn(employee1).when(repository).save(any());

		User savedUser = service.createUser(employee1);

		Assertions.assertNotNull(savedUser, "The saved User should not be null");
	}

	@Test
	@DisplayName("Test findAll")
	void testFindAll() {
		User employee1 = new User("e1001", "adam", "Gilly", 10.00, "12/01/2021");
		User employee2 = new User("e1002", "Harry", "Potter", 10.00, "10/01/2021");
		doReturn(Arrays.asList(employee1, employee2)).when(repository).findAll();

		List<User> users = service.getUsers();

		Assertions.assertEquals(2, users.size(), "findAll should return 2 users");
	}
}
