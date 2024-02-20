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

import server.atena.models.Feedback;
import server.atena.models.SearchCriteria;
import server.atena.service.FeedbackService;
									
@RestController
@RequestMapping("/api/feedback") // Dostosuj ścieżkę URL do swoich potrzeb
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {
	
    private final FeedbackService service;

    @Autowired
    public FeedbackController(FeedbackService service) {
        this.service = service;
    }
    
    @PostMapping("/add") 
    public ResponseEntity<Feedback> add(@RequestBody String json_rateCC) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        Feedback feedback = null;
        try {
        	feedback = objectMapper.readValue(json_rateCC, Feedback.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
        Feedback addedFeedback = service.add(feedback);
        
		return new ResponseEntity<>(addedFeedback, HttpStatus.OK);
     
    }
    
	@PostMapping("/addList")
	public void addList(@RequestBody String json_rateCC) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			List<Feedback> myObjectList = objectMapper.readValue(json_rateCC, new TypeReference<List<Feedback>>() {
			});

			for (Feedback feedback : myObjectList) {
				service.add(feedback);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    
	@PostMapping("/search")
	public List<Feedback> searchNotes(@RequestBody List<SearchCriteria> params) {
		return service.searchRates(params);
	}
    
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.delete(id);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Feedback> getById(@PathVariable Long id) {
		Feedback feedback = service.getById(id);
		return ResponseEntity.ok(feedback);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<Feedback>> getAll() {
		Iterable<Feedback> feedback = service.getAll();
		return ResponseEntity.ok(feedback);
	}
	
	@GetMapping("/getAllFeedbackDates/{dateStart}/{dateEnd}")
	public ResponseEntity<Iterable<Feedback>> getAllNoteDates(@PathVariable String dateStart,
			@PathVariable String dateEnd) {
		Iterable<Feedback> feedbackList = service.getAllNoteDates(dateStart, dateEnd);
		return ResponseEntity.ok(feedbackList);

	}

}
