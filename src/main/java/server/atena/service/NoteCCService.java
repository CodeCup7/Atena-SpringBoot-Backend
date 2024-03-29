package server.atena.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import server.atena.app.enums.NotificationMode;
import server.atena.app.enums.NotificationType;
import server.atena.app.enums.StatusNote;
import server.atena.models.NoteCC;
import server.atena.models.Notification;
import server.atena.models.SearchCriteria;
import server.atena.models.User;
import server.atena.repositories.NoteCCRepository;
import server.atena.repositories.NotificationRepository;
import server.atena.repositories.RateCCRepository;
import server.atena.repositories.RateMRepository;

@Service
public class NoteCCService {

	private final NoteCCRepository repository;
	private final RateCCRepository repositoryRateCC;
	private final RateMRepository repositoryRateM;
	private final NotificationRepository notiRepo;

	@Autowired
	public NoteCCService(NoteCCRepository repository, RateCCRepository repositoryRateCC,
			RateMRepository repositoryRateM, NotificationRepository notiRepo) {
		this.repository = repository;
		this.repositoryRateCC = repositoryRateCC;
		this.repositoryRateM = repositoryRateM;
		this.notiRepo = notiRepo;
	}

	public NoteCC add(NoteCC noteCC) {
		NoteCC addedNoteCC = repository.save(noteCC);

		// Aktualizacja ocen wchodzących w skład coachingu
		addedNoteCC.getRateM_Col().forEach(rateM -> {
			rateM.setNoteCC(noteCC);
			repositoryRateM.save(rateM);
		});
		addedNoteCC.getRateCC_Col().forEach(rateCC -> {
			rateCC.setNoteCC(noteCC);
			repositoryRateCC.save(rateCC);
		});

		// Dodanie powiadomienia o nowej ocenie
		Notification noti = new Notification();
		noti.setAgent(addedNoteCC.getAgent());
		noti.setMode(NotificationMode.PUSH_);
		noti.setPreviewId(addedNoteCC.getId());
		noti.setType(NotificationType.NOTE_CC_);
		noti.setText("Masz dostęp do nowego coachingu");

		notiRepo.save(noti);

		return addedNoteCC;

	}

	public List<NoteCC> searchNotes(List<SearchCriteria> params) {

	    List<Specification<NoteCC>> ANDSpecs = new ArrayList<>();
	    List<Specification<NoteCC>> ORSpecs = new ArrayList<>();

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
	                    } else if (param.getValue().equals("APPEAL_")) {
	                        param.setValue(StatusNote.APPEAL_);
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

	    Specification<NoteCC> finalANDSpec = ANDSpecs.isEmpty() ? null : ANDSpecs.get(0);
	    for (int i = 1; i < ANDSpecs.size(); i++) {
	        finalANDSpec = finalANDSpec.and(ANDSpecs.get(i));
	    }

	    Specification<NoteCC> finalORSpec = ORSpecs.isEmpty() ? null : ORSpecs.get(0);
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
	        return (List<NoteCC>) repository.findAll();
	    }
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

	public void update(NoteCC noteCC) {

		NoteCC updateNoteCC = repository.save(noteCC);

		if (noteCC.getStatus().equals(StatusNote.APPEAL_)) {

			// Dodanie powiadomienia o odwołaniu
			Notification noti = new Notification();
			noti.setCoach(updateNoteCC.getCoach());
			noti.setMode(NotificationMode.PUSH_);
			noti.setPreviewId(updateNoteCC.getId());
			noti.setType(NotificationType.NOTE_APPEALS_);
			noti.setText("Nowe odwołanie agenta!");

			notiRepo.save(noti);
		}
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Iterable<NoteCC> getAllNote() {
		return repository.findAll();
	}

}
