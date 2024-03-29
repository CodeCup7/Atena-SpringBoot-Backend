package server.atena.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.User;
import server.atena.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	private final UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping("/add")
	public void add(@RequestBody String json_rateCC) {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = objectMapper.readValue(json_rateCC, User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		service.add(user);

	}

	@PostMapping("/update")
	public void update(@RequestBody String json_rateCC) {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = objectMapper.readValue(json_rateCC, User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		service.update(user);

	}

	@PostMapping("/addList")
	public void addList(@RequestBody String json_rateCC) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			List<User> myObjectList = objectMapper.readValue(json_rateCC, new TypeReference<List<User>>() {
			});

			for (User user : myObjectList) {
				service.add(user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@GetMapping("/getUserId/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = service.getUserById(id);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/getUserLogin/{login}")
	public ResponseEntity<User> getUserLogin(@PathVariable String login) {
		User user = service.getUserByLogin(login);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/getUserAll")
	public ResponseEntity<Iterable<User>> getAllUsers() {
		Iterable<User> users = service.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.delete(id);
	}

}
