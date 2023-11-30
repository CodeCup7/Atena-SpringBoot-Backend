package server.atena.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.atena.models.Queue;
import server.atena.models.User;
import server.atena.service.UserService;

@RestController
@RequestMapping("/api/user") // Dostosuj ścieżkę URL do swoich potrzeb
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/add") // Zmieniamy na POST, bo to jest operacja zapisu
    public void add(@RequestBody String json_rateCC) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
        	user = objectMapper.readValue(json_rateCC, User.class);
            // Tutaj możesz przetwarzać obiekt RateCC
        } catch (IOException e) {
            e.printStackTrace();
            // Obsłuż błąd deserializacji JSON
        }
        System.out.println(user);
    	service.add(user);
       
    }
    
    @PostMapping("/addList") // Zmieniamy na POST, bo to jest operacja zapisu
    public void addList(@RequestBody String json_rateCC) {
    	ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<User> myObjectList = objectMapper.readValue(json_rateCC, new TypeReference<List<User>>() {});

            for (User user : myObjectList) {
                service.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
}
