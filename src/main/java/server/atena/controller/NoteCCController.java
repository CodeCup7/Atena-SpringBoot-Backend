package server.atena.controller;

import java.io.IOException;
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

import server.atena.models.Feedback;
import server.atena.models.NoteCC;
import server.atena.models.RateCC;
import server.atena.models.RateM;
import server.atena.models.SearchCriteria;
import server.atena.service.NoteCCService;
import server.atena.service.RateCCService;
import server.atena.service.RateMService;

@RestController
@RequestMapping("/api/noteCC")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteCCController {

	private final NoteCCService service;
	private final RateCCService serviceRateCC;
	private final RateMService serviceRateM;

	@Value("${attachment.upload.dir}")
	private String uploadDir;
	
	@Autowired
	public NoteCCController(NoteCCService service, RateCCService serviceRateCC, RateMService serviceRateM) {
		this.service = service;
		this.serviceRateCC = serviceRateCC;
		this.serviceRateM = serviceRateM;
	}

	@PostMapping("/add")
	public ResponseEntity<NoteCC> add(@RequestBody String json_rateCC)
			throws JsonMappingException, JsonProcessingException {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			final NoteCC noteCC = objectMapper.readValue(json_rateCC, NoteCC.class);

			NoteCC addedNoteCC = service.add(noteCC);

			return new ResponseEntity<>(addedNoteCC, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/addList")
	public void addList(@RequestBody String json_rateCC) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			List<NoteCC> myObjectList = objectMapper.readValue(json_rateCC, new TypeReference<List<NoteCC>>() {
			});

			for (NoteCC noteCC : myObjectList) {
				service.add(noteCC);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@PostMapping("/update")
	public void update(@RequestBody String json_noteCC) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		NoteCC noteCC = objectMapper.readValue(json_noteCC, NoteCC.class);

		service.update(noteCC);
		
	}
	
	@GetMapping("/getNoteAll")
	public ResponseEntity<Iterable<NoteCC>> getAllNote() {
		Iterable<NoteCC> noteList = service.getAllNote();
		return ResponseEntity.ok(noteList);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<NoteCC> getById(@PathVariable Long id) {
		NoteCC noteCC = service.getById(id);
		return ResponseEntity.ok(noteCC);
	}

	@PostMapping("/search")
	public List<NoteCC> searchNotes(@RequestBody List<SearchCriteria> params) {
		return service.searchNotes(params);
	}

	@DeleteMapping("/delete")
	public void deleteById(@RequestBody String json_noteCC) {
		ObjectMapper objectMapper = new ObjectMapper();
		NoteCC noteCC = null;
		try {
			noteCC = objectMapper.readValue(json_noteCC, NoteCC.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Pobranie wszystkich RateCC gdzie noteCC_id = id i ustawienie tych note_id na
		// NULL (wyzerowanie ocen rateCC względem noteCC - skasowanie relacji)
		Iterable<RateCC> rateCCList = serviceRateCC.getAllRateCCByNoteId(noteCC);
		rateCCList.forEach(e -> {
			e.setNoteCC(null);
			serviceRateCC.update(e);
		});

		// Pobranie wszystkich RateM gdzie noteCC_id = id i ustawienie tych note_id na
		// NULL (wyzerowanie ocen rateM względem noteCC - skasowanie relacji)
		Iterable<RateM> rateMList = serviceRateM.getAllRateMByNoteId(noteCC);
		rateMList.forEach(e -> {
			e.setNoteCC(null);
			serviceRateM.update(e);
		});

		service.deleteById(noteCC.getId());
	}
	
	@PostMapping("/export")
	public ResponseEntity<Resource> exportToFile(@RequestBody String json_noteCC) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			NoteCC noteCC = objectMapper.readValue(json_noteCC, NoteCC.class);

			String fileName = String.valueOf(noteCC.getId());
			Path filePath = Path.of(uploadDir).resolve(fileName + ".json"); // Dodaj ".json" do nazwy pliku

			Files.writeString(filePath, json_noteCC); // Zapisz dane JSON do pliku

			Resource resource = new UrlResource(filePath.toUri());

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
