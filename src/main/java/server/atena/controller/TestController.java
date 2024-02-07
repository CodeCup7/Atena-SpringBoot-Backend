package server.atena.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import server.atena.models.Test;
import server.atena.service.TestService;

@RestController
@RequestMapping("/api/test") // Dostosuj ścieżkę URL do swoich potrzeb
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

	private final TestService service;

	@Autowired
	public TestController(TestService service) {
		this.service = service;
	}

	@PostMapping("/add")
	public ResponseEntity<Test> add(@RequestBody String json_rateCC) {

		ObjectMapper objectMapper = new ObjectMapper();
		Test test = null;
		try {
			test = objectMapper.readValue(json_rateCC, Test.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Test addedTest = service.add(test);

		return new ResponseEntity<>(addedTest, HttpStatus.OK);

	}

	@PostMapping("/addList")
	public void addList(@RequestBody String json_rateCC) {

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<Test> myObjectList = objectMapper.readValue(json_rateCC, new TypeReference<List<Test>>() {
			});

			for (Test test : myObjectList) {
				service.add(test);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.delete(id);
	}

	@GetMapping("/getAll")
	public ResponseEntity<Iterable<Test>> getAll() {
		Iterable<Test> test = service.getAll();
		return ResponseEntity.ok(test);
	}

	@GetMapping("/getAllTestDates/{dateStart}/{dateEnd}")
	public ResponseEntity<Iterable<Test>> getAllNoteDates(@PathVariable String dateStart,
			@PathVariable String dateEnd) {
		Iterable<Test> testList = service.getAllNoteDates(dateStart, dateEnd);
		return ResponseEntity.ok(testList);

	}

}
