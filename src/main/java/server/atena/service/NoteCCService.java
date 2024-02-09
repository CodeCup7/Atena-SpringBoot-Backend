package server.atena.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import server.atena.app.enums.StatusNote;
import server.atena.models.NoteCC;
import server.atena.models.SearchCriteria;
import server.atena.models.User;
import server.atena.repositories.NoteCCRepository;
import server.atena.repositories.RateCCRepository;

@Service
public class NoteCCService {

	private final NoteCCRepository repository;
	private final RateCCRepository repositoryRateCC;

	@Autowired
	public NoteCCService(NoteCCRepository repository, RateCCRepository repositoryRateCC) {
		this.repository = repository;
		this.repositoryRateCC = repositoryRateCC;
	}

	public List<NoteCC> searchNotes(List<SearchCriteria> params) {

		List<Specification<NoteCC>> specs = new ArrayList<>();

		for (SearchCriteria param : params) {
			Specification<NoteCC> spec = (root, query, builder) -> {
				if (param.getOperation().equalsIgnoreCase("BETWEEN")) {
					if ("appliesDate".equals(param.getKey()) || "coachDate".equals(param.getKey())) {
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
						Join<NoteCC, User> agentJoin = root.join("agent");
						return builder.equal(agentJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else if ("coach".equals(param.getKey())) {
						Join<NoteCC, User> coachJoin = root.join("coach");
						return builder.equal(coachJoin.get("id"), Long.parseLong(param.getValue().toString()));
					} else if ("status".equals(param.getKey())) {

						if (param.getValue().equals("CLOSE_")) {
							param.setValue(StatusNote.CLOSE_);
							return builder.equal(root.get(param.getKey()), param.getValue());
						} else if (param.getValue().equals("CLOSE_WITHOUT_")) {
							param.setValue(StatusNote.CLOSE_WITHOUT_);
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

		Specification<NoteCC> finalSpec = Specification.where(specs.get(0));
		for (int i = 1; i < specs.size(); i++) {
			finalSpec = finalSpec.or(specs.get(i));
		}

		return repository.findAll(finalSpec);
	}

	public NoteCC add(NoteCC noteCC) {
		NoteCC addedNoteCC = repository.save(noteCC);

		addedNoteCC.getRateCC_Col().forEach(rateCC -> {
			rateCC.setNoteCC(noteCC);
			repositoryRateCC.save(rateCC);
		});
		return addedNoteCC;

	}

	public NoteCC getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<NoteCC> getAllNotes() {
		Iterable<NoteCC> iterable = repository.findAll();
		List<NoteCC> NoteList = new ArrayList<>();
		iterable.forEach(NoteList::add);
		return NoteList;
	}

	public List<NoteCC> getAllNoteDates(String startDate, String endDate) {
		Iterable<NoteCC> iterable = repository.getAllNoteDates(startDate, endDate);
		List<NoteCC> NoteList = new ArrayList<>();
		iterable.forEach(NoteList::add);
		return NoteList;
	}

	public void update(NoteCC noteCC) {
		repository.save(noteCC);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Iterable<NoteCC> getAllNote() {
		return repository.findAll();
	}

}
