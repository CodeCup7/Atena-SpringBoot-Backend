package server.atena.controller;

import java.io.IOException;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.RateCC;
import server.atena.models.User;
import server.atena.service.RateCCService;

@RestController
@RequestMapping("/api/rateCC")
@CrossOrigin(origins = "http://localhost:3000")
public class RateCCController {

	private final RateCCService service;

	@Autowired
	public RateCCController(RateCCService service) {
		this.service = service;
	}

	@PostMapping("/add")
	public void add(@RequestBody String json_rateCC) throws JsonMappingException, JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		final RateCC rateCC = objectMapper.readValue(json_rateCC, RateCC.class);

		service.add(rateCC);

	}

	// Pobiera wszystkie RateCC które nie są przypisane do coachingu
	@GetMapping("/getAllRates")
	public ResponseEntity<Iterable<RateCC>> getAllRates() {
		Iterable<RateCC> rates = service.getAllRates();
		return ResponseEntity.ok(rates);
	}

	// Pobiera wszystkie RateCC które nie są przypisane do coachingu
	@GetMapping("/getAllRateNoNote")
	public ResponseEntity<Iterable<RateCC>> getAllRateNoNote() {
		Iterable<RateCC> rates = service.getAllRateNoNote();
		return ResponseEntity.ok(rates);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<RateCC> getById(@PathVariable Long id) {
		RateCC rateCC = service.getById(id);
		return ResponseEntity.ok(rateCC);
	}

	@PostMapping("/update")
	public void update(@RequestBody String json_rateCC) throws JsonMappingException, JsonProcessingException  {
		ObjectMapper objectMapper = new ObjectMapper();
		final RateCC rateCC = objectMapper.readValue(json_rateCC, RateCC.class);

		service.update(rateCC);

	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}

}
