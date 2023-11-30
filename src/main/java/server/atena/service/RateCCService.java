package server.atena.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.atena.models.RateCC;
import server.atena.repositories.RateCCRepository;

@Service
public class RateCCService {
	
    private final RateCCRepository repository;

    @Autowired
    public RateCCService(RateCCRepository repository) {
        this.repository = repository;
    }
    
    public void add(RateCC e) {
        repository.save(e);
    }
    
    public RateCC getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<RateCC> getAllRates() {
        Iterable<RateCC> iterable = repository.findAll();
        List<RateCC> rateList = new ArrayList<>();
        iterable.forEach(rateList::add);
        return rateList;
    }

    public void update(RateCC rateCC) {
        repository.save(rateCC);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}