package server.atena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.RateCC;
import server.atena.service.RateCCService;

@RestController
@RequestMapping("/api/rateCC") // Dostosuj ścieżkę URL do swoich potrzeb
@CrossOrigin(origins = "http://localhost:3000")
public class RateCCController {
	
	private final RateCCService service;

    @Autowired
    public RateCCController(RateCCService service) {
        this.service = service;
    }
    
    @PostMapping("/add") // Zmieniamy na POST, bo to jest operacja zapisu
    public void add(@RequestBody String json_rateCC) throws JsonMappingException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        final RateCC rateCC = objectMapper.readValue(json_rateCC, RateCC.class);;

        rateCC.getRatePart().forEach(e->{
        	e.setRate(rateCC);
        });
    	
        service.add(rateCC);
        
    }
}
