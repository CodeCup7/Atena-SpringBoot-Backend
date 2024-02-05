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

import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.Feedback;
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
    public void add(@RequestBody String json_rateCC) {
        ObjectMapper objectMapper = new ObjectMapper();
        Feedback feedback = null;
        try {
        	feedback = objectMapper.readValue(json_rateCC, Feedback.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
        service.add(feedback);
     
    }
    
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.delete(id);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<Feedback>> getAll() {
		Iterable<Feedback> feedback = service.getAll();
		return ResponseEntity.ok(feedback);
	}

}