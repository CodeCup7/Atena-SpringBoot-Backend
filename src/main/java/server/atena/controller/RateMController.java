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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.atena.models.NoteCC;
import server.atena.models.RateM;
import server.atena.models.SearchCriteria;
import server.atena.models.User;
import server.atena.service.RateMService;

@RestController
@RequestMapping("/api/RateM")
@CrossOrigin(origins = "http://localhost:3000")
public class RateMController {

	private final RateMService service;

	@Autowired
	public RateMController(RateMService service) {
		this.service = service;
	}

	@PostMapping("/add")
	public ResponseEntity<RateM> add(@RequestBody String json_RateM)
			throws JsonMappingException, JsonProcessingException {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			final RateM RateM = objectMapper.readValue(json_RateM, RateM.class);

			RateM addedRateM = service.add(RateM);

			return new ResponseEntity<>(addedRateM, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/search")
	public List<RateM> searchNotes(@RequestBody List<SearchCriteria> params) {
		return service.searchRates(params);
	}

	@GetMapping("/getAllRates")
	public ResponseEntity<Iterable<RateM>> getAllRates() {
		Iterable<RateM> rates = service.getAllRates();
		return ResponseEntity.ok(rates);
	}

	// Pobiera wszystkie RateM które nie są przypisane do coachingu
	@GetMapping("/getAllRateNoNote")
	public ResponseEntity<Iterable<RateM>> getAllRateNoNote() {
		Iterable<RateM> rates = service.getAllRateNoNote();
		return ResponseEntity.ok(rates);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<RateM> getById(@PathVariable Long id) {
		RateM RateM = service.getById(id);
		return ResponseEntity.ok(RateM);
	}

	@PostMapping("/update")
	public void update(@RequestBody String json_RateM) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		final RateM RateM = objectMapper.readValue(json_RateM, RateM.class);

		service.update(RateM);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}

}
