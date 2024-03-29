package server.atena.controller;

import java.io.IOException;
import java.util.List;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.Queue;
import server.atena.service.QueueService;
									
@RestController
@RequestMapping("/api/queue") // Dostosuj ścieżkę URL do swoich potrzeb
@CrossOrigin(origins = "http://localhost:3000")
public class QueueController {
	
    private final QueueService service;

    @Autowired
    public QueueController(QueueService service) {
        this.service = service;
    }
    
    @PostMapping("/add") 
    public void add(@RequestBody String json_rateCC) {
        ObjectMapper objectMapper = new ObjectMapper();
        Queue queue = null;
        try {
        	queue = objectMapper.readValue(json_rateCC, Queue.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
        service.add(queue);
        
    }
    
    @PostMapping("/addList") 
    public void addList(@RequestBody String json_rateCC) {
    	ObjectMapper objectMapper = new ObjectMapper();

        try {	
            List<Queue> myObjectList = objectMapper.readValue(json_rateCC, new TypeReference<List<Queue>>() {});

            for (Queue queue : myObjectList) {
                service.add(queue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    
	@PostMapping("/update")
	public void update(@RequestBody String json_rateCC) {
		ObjectMapper objectMapper = new ObjectMapper();
		Queue queue = null;
		try {
			queue = objectMapper.readValue(json_rateCC, Queue.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		service.update(queue);

	}
    
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		service.delete(id);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<Queue>> getAll() {
		Iterable<Queue> queue = service.getAll();
		return ResponseEntity.ok(queue);
	}

}
