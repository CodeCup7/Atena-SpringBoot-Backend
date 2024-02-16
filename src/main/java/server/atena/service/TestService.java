package server.atena.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import server.atena.app.enums.TestPass;
import server.atena.models.SearchCriteria;
import server.atena.models.Test;
import server.atena.models.User;
import server.atena.repositories.TestRepository;

@Service
public class TestService {

	private final TestRepository repository;

	@Autowired
	public TestService(TestRepository repository) {
		this.repository = repository;
	}

	public Test add(Test test) {
		Test addedTest = repository.save(test);
		return addedTest;
	}

	public List<Test> searchRates(List<SearchCriteria> params) {
		List<Specification<Test>> ANDSpecs = new ArrayList<>();
		List<Specification<Test>> ORSpecs = new ArrayList<>();

		for (SearchCriteria param : params) {
			Specification<Test> spec = (root, query, builder) -> {

				if (param.getOperation().equalsIgnoreCase("BETWEEN")) {
					if ("dateTest".equals(param.getKey())) {
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
						Join<Test, User> agentJoin = root.join("agent");
						return builder.equal(agentJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else if ("testPass".equals(param.getKey())) {
						if (param.getValue().equals("NO_PASS_")) {
							param.setValue(TestPass.NO_PASS_);
							return builder.equal(root.get(param.getKey()), param.getValue());
						} else if (param.getValue().equals("NO_REQUIRED_")) {
							param.setValue(TestPass.NO_REQUIRED_);
							return builder.equal(root.get(param.getKey()), param.getValue());
						} else if (param.getValue().equals("PASS_")) {
							param.setValue(TestPass.PASS_);
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

		Specification<Test> finalANDSpec = ANDSpecs.isEmpty() ? null : ANDSpecs.get(0);
		for (int i = 1; i < ANDSpecs.size(); i++) {
			finalANDSpec = finalANDSpec.and(ANDSpecs.get(i));
		}

		Specification<Test> finalORSpec = ORSpecs.isEmpty() ? null : ORSpecs.get(0);
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
			return (List<Test>) repository.findAll();
		}

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
