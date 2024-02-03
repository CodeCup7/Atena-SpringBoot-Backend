package server.atena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.atena.models.Queue;
import server.atena.repositories.QueueRepository;

@Service
public class QueueService {
	
    private final QueueRepository repository;

    @Autowired
    public QueueService(QueueRepository repository) {
        this.repository = repository;
    }
    
    public void add(Queue e) {
        repository.save(e);
    }
    	
    public void delete(Long e) {
        repository.deleteById(e);
    }
    
    public Queue getById(Long id) {
        return repository.findById(id).orElse(null);
    }
    

    public Iterable<Queue> getAll() {
        return repository.findAll();
    }
    
    public void update(Queue e) {
        repository.save(e);
    }

}
