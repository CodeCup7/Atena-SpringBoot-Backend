package server.atena.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.Queue;
import server.atena.models.RateCC;
import server.atena.models.User;
import server.atena.service.QueueService;
import server.atena.service.UserService;

@RestController
@RequestMapping("/api/queue") // Dostosuj ścieżkę URL do swoich potrzeb
@CrossOrigin(origins = "http://localhost:3000")
public class QueueController {
	
    private final QueueService service;

    @Autowired
    public QueueController(QueueService service) {
        this.service = service;
    }
    
    @PostMapping("/add") // Zmieniamy na POST, bo to jest operacja zapisu
    public void add(@RequestBody String json_rateCC) {
        ObjectMapper objectMapper = new ObjectMapper();
        Queue queue = null;
        try {
        	queue = objectMapper.readValue(json_rateCC, Queue.class);
            // Tutaj możesz przetwarzać obiekt RateCC
        } catch (IOException e) {
            e.printStackTrace();
            // Obsłuż błąd deserializacji JSON
        }
    	
        //rateCC. name = rateCC.get("name");
        System.out.println(queue);
       
        service.add(queue);
        
    }
    
    @PostMapping("/addList") // Zmieniamy na POST, bo to jest operacja zapisu
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

}
