package server.atena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.atena.models.Queue;
import server.atena.models.User;
import server.atena.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    
    public void add(User e) {
        repository.save(e);
    }
}
