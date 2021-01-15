package com.nphc.task.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.task.entity.User;
import com.nphc.task.helper.CSVHelper;
import com.nphc.task.helper.ParameterValidator;
import com.nphc.task.message.GetAllUsersResponse;
import com.nphc.task.message.ResponseMessage;
import com.nphc.task.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (CSVHelper.hasCSVFormat(file)) {
			try {
				userService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping
	public ResponseEntity<GetAllUsersResponse> getAllUsers() {
		try {
			List<User> userList = userService.getUsers();

			if (userList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(new GetAllUsersResponse(userList), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> createOrUpdateEmployee(@RequestBody User user) {
		String message = "";
		if(user.getSalary() == 0.00) {
			message = "Invalid salary ";

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		}
		if(!ParameterValidator.checkDateFormat(user.getStartDate())) {
			message = "Invalid date ";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		}
		User updated = userService.createUser(user);
		message = "New Employee Record Created: " + updated.getId();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable String id) throws Exception {
		Optional<User> emp = userService.getUserById(id);
		if (!emp.isPresent())
			throw new Exception("Could not find employee with id- " + id);

		return emp.get();
	}

	@PutMapping
	public ResponseEntity<ResponseMessage> updateEmployee(@Valid @RequestBody User user) {
		String message = "";
		
		if(user.getSalary() == 0.00) {
			message = "Invalid salary " ;

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		}

		if(!ParameterValidator.checkDateFormat(user.getStartDate())) {
			message = "Invalid date ";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		}
	
		
		Optional<User> userOptional = userService.getUserById(user.getId());
		if (!userOptional.isPresent()) {
			message = "No such employee - " + user.getId();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		}

		userService.createUser(user);
		message = "Successfully Updated : " + user.getId();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseMessage> deleteEmployeeById(@PathVariable("id") String id) {
		String message = "";
		Optional<User> userOptional = userService.getUserById(id);

		if (!userOptional.isPresent()) {
			message = "No such employee - " + id;
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		}
		
		userService.deleteUserById(id);

		message = "Successfully Deleted : " + id;
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	}

}
