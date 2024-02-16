package server.atena.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import server.atena.app.enums.FeedbackType;
import server.atena.models.Feedback;
import server.atena.models.SearchCriteria;
import server.atena.models.User;
import server.atena.repositories.FeedbackRepository;

@Service
public class FeedbackService {

	private final FeedbackRepository repository;

	@Autowired
	public FeedbackService(FeedbackRepository repository) {
		this.repository = repository;
	}

	public Feedback add(Feedback feedback) {
		Feedback addedFeedback = repository.save(feedback);
		return addedFeedback;
	}

	public List<Feedback> searchRates(List<SearchCriteria> params) {
		List<Specification<Feedback>> ANDSpecs = new ArrayList<>();
		List<Specification<Feedback>> ORSpecs = new ArrayList<>();

		for (SearchCriteria param : params) {
			Specification<Feedback> spec = (root, query, builder) -> {

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
						Join<Feedback, User> agentJoin = root.join("agent");
						return builder.equal(agentJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else if ("testPass".equals(param.getKey())) {
						if (param.getValue().equals("POSITIVE_")) {
							param.setValue(FeedbackType.POSITIVE_);
							return builder.equal(root.get(param.getKey()), param.getValue());
						} else if (param.getValue().equals("NEGATIVE_")) {
							param.setValue(FeedbackType.NEGATIVE_);
							return builder.equal(root.get(param.getKey()), param.getValue());
						}
					} else {
						return builder.equal(root.get(param.getKey()), param.getValue());
					}
				}

				return null;
			};
			if ("agent".equals(param.getKey()) || "coach".equals(param.getKey())) {
				ORSpecs.add(spec);
			} else {
				ANDSpecs.add(spec);
			}
		}

		Specification<Feedback> finalANDSpec = ANDSpecs.isEmpty() ? null : ANDSpecs.get(0);
		for (int i = 1; i < ANDSpecs.size(); i++) {
			finalANDSpec = finalANDSpec.and(ANDSpecs.get(i));
		}

		Specification<Feedback> finalORSpec = ORSpecs.isEmpty() ? null : ORSpecs.get(0);
		for (int i = 1; i < ORSpecs.size(); i++) {
			finalORSpec = finalORSpec.or(ORSpecs.get(i));
		}

		if (finalANDSpec != null && finalORSpec != null) {
			return repository.findAll(finalANDSpec.and(finalORSpec));
		} else if (finalANDSpec != null) {
			return repository.findAll(finalANDSpec);
		} else if (finalORSpec != null) {
			return repository.findAll(finalORSpec);
		} else {
			return (List<Feedback>) repository.findAll();
		}

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
