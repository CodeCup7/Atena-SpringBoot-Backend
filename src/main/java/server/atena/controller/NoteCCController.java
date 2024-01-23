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

import server.atena.models.NoteCC;
import server.atena.models.User;
import server.atena.service.NoteCCService;

@RestController
@RequestMapping("/api/noteCC")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteCCController {
	
	private final NoteCCService service;

    @Autowired
    public NoteCCController(NoteCCService service) {
        this.service = service;
    }
    
    @PostMapping("/add") 
    public void add(@RequestBody String json_rateCC) throws JsonMappingException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        final NoteCC noteCC = objectMapper.readValue(json_rateCC, NoteCC.class);;

        service.add(noteCC);
        
    }
    
    @GetMapping("/getNoteAll")
    public ResponseEntity<Iterable<NoteCC>> getAllNote() {
        Iterable<NoteCC> noteList = service.getAllNote();
        return ResponseEntity.ok(noteList);
    }
    
    @GetMapping("/getAllNoteBetweenDates/{dateStart}/{dateEnd}")
    public ResponseEntity<Iterable<NoteCC>> getAllNoteDates(@PathVariable String  dateStart, @PathVariable String  dateEnd) {
        Iterable<NoteCC> noteList = service.getAllNoteDates(dateStart, dateEnd);
        return ResponseEntity.ok(noteList);
        
    } 
    
	@PostMapping("/update")
	public void update(@RequestBody String json_rateCC) {
		ObjectMapper objectMapper = new ObjectMapper();
		NoteCC noteCC = null;
		try {
			noteCC = objectMapper.readValue(json_rateCC, NoteCC.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		service.update(noteCC);

	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}
    

    
}
