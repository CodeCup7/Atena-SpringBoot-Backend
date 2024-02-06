package server.atena.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.atena.models.Test;
import server.atena.repositories.TestRepository;

@Service
public class TestService {
	
    private final TestRepository repository;

    @Autowired
    public TestService(TestRepository repository) {
        this.repository = repository;
    }
    
    public Test add(Test test) {
    	Test addedTest= repository.save(test);
		return addedTest;
    }
    	
    public void delete(Long e) {
        repository.deleteById(e);
    }
    
    public Test getById(Long id) {
        return repository.findById(id).orElse(null);
    }
    

    public Iterable<Test> getAll() {
        return repository.findAll();
    }
    
    public void update(Test e) {
        repository.save(e);
    }
    
	public List<Test> getAllNoteDates(String startDate, String endDate) {
		Iterable<Test> iterable = repository.getAllTestsDates(startDate, endDate);
		List<Test> testList = new ArrayList<>();
		iterable.forEach(testList::add);
		return testList;
	}

}
