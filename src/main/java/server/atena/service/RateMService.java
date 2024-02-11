package server.atena.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import server.atena.models.NoteCC;
import server.atena.models.RateM;
import server.atena.models.SearchCriteria;
import server.atena.models.User;
import server.atena.repositories.RateMRepository;

@Service
public class RateMService {

	private final RateMRepository repository;

	@Autowired
	public RateMService(RateMRepository repository) {
		this.repository = repository;
	}

	public RateM add(RateM RateM) {
		RateM addedRateM = repository.save(RateM);
		return addedRateM;
	}

	public RateM getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<RateM> searchRates(List<SearchCriteria> params) {
    	List<Specification<RateM>> specs = new ArrayList<>();

	    for (SearchCriteria param : params) {
	        Specification<RateM> spec = (root, query, builder) -> {

				if (param.getOperation().equalsIgnoreCase("BETWEEN")) {
					if ("dateRate".equals(param.getKey())) {
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
					
					if ("agent".equals(param.getKey())) {
						Join<RateM, User> agentJoin = root.join("agent");
						return builder.equal(agentJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else if ("coach".equals(param.getKey())) {
						Join<RateM, User> coachJoin = root.join("coach");
						return builder.equal(coachJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else {
						return builder.equal(root.get(param.getKey()), param.getValue());
					}
				}

				return null;
	        };
	        specs.add(spec);
	    }

	    Specification<RateM> finalSpec = Specification.where(specs.get(0));
	    for (int i = 1; i < specs.size(); i++) {
	        finalSpec = finalSpec.and(specs.get(i));
	    }

	    return repository.findAll(finalSpec);
	}

	public List<RateM> getAllRates() {
		Iterable<RateM> iterable = repository.findAll();
		List<RateM> rateList = new ArrayList<>();
		iterable.forEach(rateList::add);
		return rateList;
	}

	public List<RateM> getAllRateNoNote() {
		Iterable<RateM> iterable = repository.getAllRateNoNote();
		List<RateM> rateList = new ArrayList<>();
		iterable.forEach(rateList::add);
		return rateList;
	}

	public List<RateM> getAllRateMByNoteId(NoteCC noteCC) {
		Iterable<RateM> iterable = repository.getAllRateMByNoteId(noteCC);
		List<RateM> rateList = new ArrayList<>();
		iterable.forEach(rateList::add);
		return rateList;
	}

	public void update(RateM RateM) {
		repository.save(RateM);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}