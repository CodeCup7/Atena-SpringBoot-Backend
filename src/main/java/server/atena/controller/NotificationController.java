package server.atena.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.Notification;
import server.atena.models.User;
import server.atena.service.NotificationService;
									
@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
	
    private final NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }
    
    @PostMapping("/add") 
    public ResponseEntity<Notification> add(@RequestBody String json_rateCC) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        Notification notification = null;
        try {
        	notification = objectMapper.readValue(json_rateCC, Notification.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
        Notification addedNotification = service.add(notification);
        
		return new ResponseEntity<>(addedNotification, HttpStatus.OK);
     
    }
	@PostMapping("/update")
	public void update(@RequestBody String json_rateCC) throws JsonMappingException, JsonProcessingException  {
		ObjectMapper objectMapper = new ObjectMapper();
		final Notification notification = objectMapper.readValue(json_rateCC, Notification.class);

		service.update(notification);
	}
    
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.delete(id);
	}
	
	@PostMapping("/getAll")
	public ResponseEntity<Iterable<Notification>> getAll(@RequestBody String json_user) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = objectMapper.readValue(json_user, User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Iterable<Notification> notification = service.getAll(user);
		return ResponseEntity.ok(notification);
	}
	
}
