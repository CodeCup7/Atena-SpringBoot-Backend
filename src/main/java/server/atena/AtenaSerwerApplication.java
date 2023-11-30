package server.atena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.atena.models.User;
import server.atena.repositories.UserRepository;
import server.atena.service.UserService;

@SpringBootApplication
@RestController
public class AtenaSerwerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtenaSerwerApplication.class, args);
		
	}
	

}
