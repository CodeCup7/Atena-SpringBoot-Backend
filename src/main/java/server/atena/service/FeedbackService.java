package server.atena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.atena.models.Feedback;
import server.atena.repositories.FeedbackRepository;

@Service
public class FeedbackService {
	
    private final FeedbackRepository repository;

    @Autowired
    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }
    
    public void add(Feedback e) {
        repository.save(e);
    }
    	
    public void delete(Long e) {
        repository.deleteById(e);
    }
    
    public Feedback getById(Long id) {
        return repository.findById(id).orElse(null);
    }
    

    public Iterable<Feedback> getAll() {
        return repository.findAll();
    }
    
    public void update(Feedback e) {
        repository.save(e);
    }

}
