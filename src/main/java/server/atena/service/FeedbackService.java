package server.atena.service;

import java.util.ArrayList;
import java.util.List;

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
    
    public Feedback add(Feedback feedback) {
    	Feedback addedFeedback= repository.save(feedback);
		return addedFeedback;
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
    
	public List<Feedback> getAllNoteDates(String startDate, String endDate) {
		Iterable<Feedback> iterable = repository.getAllFeedbackDates(startDate, endDate);
		List<Feedback> feedbackList = new ArrayList<>();
		iterable.forEach(feedbackList::add);
		return feedbackList;
	}

}
