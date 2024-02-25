package server.atena.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.app.enums.TypeRateCC;
import server.atena.models.NoteCC;
import server.atena.models.RateCC;
import server.atena.models.SearchCriteria;
import server.atena.service.RateCCService;

@RestController
@RequestMapping("/api/rateCC")
@CrossOrigin(origins = "http://localhost:3000")
public class RateCCController {

	private final RateCCService service;

	@Value("${attachment.upload.dir}")
	private String uploadDir;

	@Autowired
	public RateCCController(RateCCService service) {
		this.service = service;
	}

	@PostMapping("/add")
	public ResponseEntity<RateCC> add(@RequestBody String json_rateCC)
			throws JsonMappingException, JsonProcessingException {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			final RateCC rateCC = objectMapper.readValue(json_rateCC, RateCC.class);
			RateCC addedRateCC = service.add(rateCC);

			return new ResponseEntity<>(addedRateCC, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/addList")
	public void addList(@RequestBody String json_rateCC) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			List<RateCC> myObjectList = objectMapper.readValue(json_rateCC, new TypeReference<List<RateCC>>() {
			});

			for (RateCC rateCC : myObjectList) {
				service.add(rateCC);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@PostMapping("/search")
	public List<RateCC> searchNotes(@RequestBody List<SearchCriteria> params) {
		return service.searchRates(params);
	}

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

	// Pobiera wszystkie oceny agenta do modyfikcaji listy coachingowej
	@GetMapping("/getAllRateNoNoteByAgent/{id}")
	public ResponseEntity<Iterable<RateCC>> getAllRateNoNoteByAgent(@PathVariable Long id) {
		Iterable<RateCC> rates = service.getAllRateNoNoteByAgent(id);
		return ResponseEntity.ok(rates);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<RateCC> getById(@PathVariable Long id) {
		RateCC rateCC = service.getById(id);
		return ResponseEntity.ok(rateCC);
	}

	@PostMapping("/updateList/{noteId}")
	public ResponseEntity<String> updateList(@RequestBody String json_rateCC, @PathVariable BigInteger noteId)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<RateCC> rateCCList = objectMapper.readValue(json_rateCC, new TypeReference<List<RateCC>>() {
		});

		service.updateList(rateCCList, noteId);
		return new ResponseEntity<>("Lista została pomyślnie zaktualizowana.", HttpStatus.OK);
	}

	@PostMapping("/deleteList")
	public ResponseEntity<String> deleteList(@RequestBody String json_rateCC)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<RateCC> rateCCList = objectMapper.readValue(json_rateCC, new TypeReference<List<RateCC>>() {
		});

		service.deleteList(rateCCList);
		return new ResponseEntity<>("Lista została pomyślnie zaktualizowana.", HttpStatus.OK);
	}

	@PostMapping("/update")
	public void update(@RequestBody String json_rateCC) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		final RateCC rateCC = objectMapper.readValue(json_rateCC, RateCC.class);

		service.update(rateCC);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}

	@PostMapping("/export")
	public ResponseEntity<Resource> exportToFile(@RequestBody String json_rateCC) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			RateCC rateCC = objectMapper.readValue(json_rateCC, RateCC.class);

			String fileName = String.valueOf(rateCC.getId());
			Path filePath = Path.of(uploadDir).resolve(fileName + ".json"); // Dodaj ".json" do nazwy pliku

			Files.writeString(filePath, json_rateCC); // Zapisz dane JSON do pliku

			Resource resource = new UrlResource(filePath.toUri());

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/deleteMysteryAndCurrent")
	public ResponseEntity<Resource> deleteMysteryAndCurrent() {

			Iterable<RateCC> rates = service.getAllRates();
			rates.forEach(e->{
				
				if(e.getTypeRate().equals(TypeRateCC.CURRENT_) || e.getTypeRate().equals(TypeRateCC.MYSTERY_)) {
					deleteById(e.getId());
				}
				
			});

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
