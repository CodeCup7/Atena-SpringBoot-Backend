package server.atena.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import server.atena.app.enums.NotificationMode;
import server.atena.app.enums.NotificationType;
import server.atena.app.enums.TypeRateCC;
import server.atena.models.NoteCC;
import server.atena.models.Notification;
import server.atena.models.Queue;
import server.atena.models.RateCC;
import server.atena.models.SearchCriteria;
import server.atena.models.User;
import server.atena.repositories.NotificationRepository;
import server.atena.repositories.RateCCRepository;

@Service
public class RateCCService {

	private final RateCCRepository repository;
	private final NotificationRepository notiRepo;

	@Autowired
	public RateCCService(RateCCRepository repository, NotificationRepository notiRepo) {
		this.repository = repository;
		this.notiRepo = notiRepo;
	}

	public RateCC add(RateCC rateCC) {
		RateCC addedRateCC = repository.save(rateCC);
		
		// Dodanie powiadomienia o nowej ocenie
		Notification noti = new Notification();
		noti.setAgent(addedRateCC.getAgent());
		noti.setMode(NotificationMode.PUSH_);
		noti.setPreviewId(addedRateCC.getId());
		
		switch(addedRateCC.getTypeRate()) {

		case ALL_:
			break;
		case CURRENT_:
			noti.setText("Masz nową ocenę z bieżącego odsłuchu");
			noti.setType(NotificationType.RATE_CC_C);
			break;
		case MYSTERY_:
			noti.setText("Masz nową ocenę z tajemniczego klienta");
			noti.setType(NotificationType.RATE_CC_M_);
			break;
		case RATTING_:
			noti.setText("Masz nową ocenę z rozmowy");
			noti.setType(NotificationType.RATE_CC_);
			break;
		default:
			break;
		}
		
		notiRepo.save(noti);

		return addedRateCC;
	}

	public RateCC getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<RateCC> searchRates(List<SearchCriteria> params) {
    	List<Specification<RateCC>> specs = new ArrayList<>();

	    for (SearchCriteria param : params) {
	        Specification<RateCC> spec = (root, query, builder) -> {
	        	
				if (param.getOperation().equalsIgnoreCase("BETWEEN")) {
					if ("dateRate".equals(param.getKey())) {
						String[] dateRange = param.getValue().toString().split(" AND ");
						String startDate = dateRange[0];
						String endDate = dateRange[1];
						return builder.between(root.get(param.getKey()), startDate, endDate);
					}

					if ("dateCall".equals(param.getKey())) {
						String[] dateRange = param.getValue().toString().split(" AND ");
						String startDate = dateRange[0];
						String endDate = dateRange[1];
						return builder.between(root.get(param.getKey()), startDate, endDate);
					}
				}
				
				if (param.getOperation().equalsIgnoreCase("LIKE")) {
					return builder.like(root.get(param.getKey()), "%" + param.getValue() + "%");
				}

				if (param.getOperation().equalsIgnoreCase(":")) {
					
					if ("queue".equals(param.getKey())) {
					    Join<RateCC, Queue> queueJoin = root.join("queue");
					    return builder.equal(queueJoin.get("id"), Long.parseLong(param.getValue().toString()));
					}
					if ("agent".equals(param.getKey())) {
						Join<RateCC, User> agentJoin = root.join("agent");
						return builder.equal(agentJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else if ("coach".equals(param.getKey())) {
						Join<RateCC, User> coachJoin = root.join("coach");
						return builder.equal(coachJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else if ("noteCC".equals(param.getKey())) {
						Join<RateCC, NoteCC> noteCCJoin = root.join("noteCC");
						return builder.equal(noteCCJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else if ("typeRate".equals(param.getKey())) {
						
						if(param.getValue().equals("RATTING_")) {
							param.setValue(TypeRateCC.RATTING_);
							return builder.equal(root.get(param.getKey()), param.getValue());
						} else if (param.getValue().equals("CURRENT_")) {
							param.setValue(TypeRateCC.CURRENT_);
							return builder.equal(root.get(param.getKey()), param.getValue());
						} else if (param.getValue().equals("MYSTERY_")) {
							param.setValue(TypeRateCC.MYSTERY_);
							return builder.equal(root.get(param.getKey()), param.getValue());
						}
					} else {
						return builder.equal(root.get(param.getKey()), param.getValue());
					}
				}
	            return null;
	        };
	        specs.add(spec);
	    }

	    Specification<RateCC> finalSpec = Specification.where(specs.get(0));
	    for (int i = 1; i < specs.size(); i++) {
	        finalSpec = finalSpec.and(specs.get(i));
	    }

	    return repository.findAll(finalSpec);
	}

	public List<RateCC> getAllRates() {
		Iterable<RateCC> iterable = repository.findAll();
		List<RateCC> rateList = new ArrayList<>();
		iterable.forEach(rateList::add);
		return rateList;
	}

	public List<RateCC> getAllRateNoNote() {
		Iterable<RateCC> iterable = repository.getAllRateNoNote();
		List<RateCC> rateList = new ArrayList<>();
		iterable.forEach(rateList::add);
		return rateList;
	}
	
	public List<RateCC> getAllRateNoNoteByAgent(long id) {
		Iterable<RateCC> iterable = repository.getAllRateNoNoteByAgent(id);
		List<RateCC> rateList = new ArrayList<>();
		iterable.forEach(rateList::add);
		return rateList;
	}

	public List<RateCC> getAllRateCCByNoteId(NoteCC noteCC) {
		Iterable<RateCC> iterable = repository.getAllRateCCByNoteId(noteCC);
		List<RateCC> rateList = new ArrayList<>();
		iterable.forEach(rateList::add);
		return rateList;
	}

	public void update(RateCC rateCC) {
		repository.save(rateCC);
	}
	public void updateList(List<RateCC> list, BigInteger noteId) {
		for(RateCC rateCC: list) {
			repository.updateList(rateCC.getId(), noteId);
		}
	}
	public void deleteList(List<RateCC> list) {
		for(RateCC rateCC: list) {
			repository.updateList(rateCC.getId(), null);
		}
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}