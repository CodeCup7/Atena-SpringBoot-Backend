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
import server.atena.models.NoteCC;
import server.atena.models.Notification;
import server.atena.models.RateM;
import server.atena.models.SearchCriteria;
import server.atena.models.User;
import server.atena.repositories.NotificationRepository;
import server.atena.repositories.RateMRepository;

@Service
public class RateMService {

	private final RateMRepository repository;
	private final NotificationRepository notiRepo;

	@Autowired
	public RateMService(RateMRepository repository, NotificationRepository notiRepo) {
		this.repository = repository;
		this.notiRepo = notiRepo;
	}

	public RateM add(RateM RateM) {
		RateM addedRateM = repository.save(RateM);

		// Dodanie powiadomienia o nowej ocenie
		Notification noti = new Notification();
		noti.setAgent(addedRateM.getAgent());
		noti.setMode(NotificationMode.PUSH_);
		noti.setPreviewId(addedRateM.getId());
		noti.setType(NotificationType.RATE_M_);
		noti.setText("Masz nową ocenę z karty maila");

		notiRepo.save(noti);

		return addedRateM;
	}

	public RateM getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<RateM> searchRates(List<SearchCriteria> params) {
		List<Specification<RateM>> ANDSpecs = new ArrayList<>();
		List<Specification<RateM>> ORSpecs = new ArrayList<>();

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
			if ("agent".equals(param.getKey()) || "coach".equals(param.getKey())) {
				ORSpecs.add(spec);
			} else {
				ANDSpecs.add(spec);
			}
		}

		Specification<RateM> finalANDSpec = ANDSpecs.isEmpty() ? null : ANDSpecs.get(0);
		for (int i = 1; i < ANDSpecs.size(); i++) {
			finalANDSpec = finalANDSpec.and(ANDSpecs.get(i));
		}

		Specification<RateM> finalORSpec = ORSpecs.isEmpty() ? null : ORSpecs.get(0);
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
			return (List<RateM>) repository.findAll();
		}
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

	public List<RateM> getAllRateNoNoteByAgent(long id) {
		Iterable<RateM> iterable = repository.getAllRateNoNoteByAgent(id);
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

	public void updateList(List<RateM> list, BigInteger noteId) {
		for (RateM rateM : list) {
			repository.updateList(rateM.getId(), noteId);
		}
	}

	public void deleteList(List<RateM> list) {
		for (RateM rateM : list) {
			repository.updateList(rateM.getId(), null);
		}
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}