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
import server.atena.models.RateCC;
import server.atena.models.SearchCriteria;
import server.atena.models.User;
import server.atena.service.NoteCCService;
import server.atena.service.RateCCService;

@RestController
@RequestMapping("/api/noteCC")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteCCController {

	private final NoteCCService service;
	private final RateCCService serviceRateCC;

	@Autowired
	public NoteCCController(NoteCCService service, RateCCService serviceRateCC) {
		this.service = service;
		this.serviceRateCC = serviceRateCC;
	}
	
	@PostMapping("/search")
    public List<NoteCC> searchNotes(@RequestBody List<SearchCriteria> params) {
        return service.searchNotes(params);
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

	@GetMapping("/getNoteAll")
	public ResponseEntity<Iterable<NoteCC>> getAllNote() {
		Iterable<NoteCC> noteList = service.getAllNote();
		return ResponseEntity.ok(noteList);
	}

	@GetMapping("/getAllNoteBetweenDates/{dateStart}/{dateEnd}")
	public ResponseEntity<Iterable<NoteCC>> getAllNoteDates(@PathVariable String dateStart,
			@PathVariable String dateEnd) {
		Iterable<NoteCC> noteList = service.getAllNoteDates(dateStart, dateEnd);
		return ResponseEntity.ok(noteList);

	}

	@PostMapping("/update")
	public void update(@RequestBody String json_noteCC) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		NoteCC noteCC = objectMapper.readValue(json_noteCC, NoteCC.class);

		service.update(noteCC);
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
		Iterable<RateCC> rateList = serviceRateCC.getAllRateCCByNoteId(noteCC);
		rateList.forEach(e -> {
			e.setNoteCC(null);
			serviceRateCC.update(e);
		});

		service.deleteById(noteCC.getId());
	}

}
