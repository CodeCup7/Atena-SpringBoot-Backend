package server.atena.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.atena.service.UserService;

@RestController
@RequestMapping("/api/users") // Dostosuj ścieżkę URL do swoich potrzeb
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add") // Zmieniamy na POST, bo to jest operacja zapisu
    public void addUser(@RequestBody Map<String, String> userData) {
        String name = userData.get("name");
        userService.addUser(name);
    }
}
