package server.atena.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.atena.models.NoteCC;
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

	public void add(NoteCC noteCC) {
		repository.save(noteCC);

		noteCC.getRateCC_Col().forEach(rateCC -> {
			rateCC.setNoteCC(noteCC);
			repositoryRateCC.save(rateCC);
		});

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

	public void update(NoteCC NoteCC) {
		repository.save(NoteCC);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Iterable<NoteCC> getAllNote() {
		return repository.findAll();
	}
}