package server.atena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.atena.models.User;
import server.atena.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    public void addUser(String name) {
        User user = new User();
        user.setNameUser(name);
        userRepository.save(user);
    }
}
