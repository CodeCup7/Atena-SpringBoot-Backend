package server.atena.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Join;
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
		Specification<NoteCC> spec = Specification.where(null);

		for (SearchCriteria param : params) {
			spec = spec.and((root, query, builder) -> {
				
				if (param.getOperation().equalsIgnoreCase("BETWEEN")) {
					if ("appliesDate".equals(param.getKey())) {
						String[] dateRange = param.getValue().toString().split(" AND ");
						String startDate = dateRange[0];
						String endDate = dateRange[1];
						return builder.between(root.get(param.getKey()), startDate, endDate);
					}
					if ("coachDate".equals(param.getKey())) {
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
					} else {
						return builder.equal(root.get(param.getKey()), param.getValue());
					}
				}
				// Dodaj obsługę innych operacji, jeśli potrzebujesz
				return null;
			});
		}

	return repository.findAll(spec,Sort.unsorted());

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
