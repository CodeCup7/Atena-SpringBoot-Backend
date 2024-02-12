package server.atena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.atena.app.enums.Role;
import server.atena.models.Notification;
import server.atena.models.User;
import server.atena.repositories.NotificationRepository;

@Service
public class NotificationService {

	private final NotificationRepository repository;

	@Autowired
	public NotificationService(NotificationRepository repository) {
		this.repository = repository;
	}

	public Notification add(Notification notification) {
		Notification addedNotification = repository.save(notification);
		return addedNotification;
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Notification getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Iterable<Notification> getAll(User user) {
		
		if(user.getRole().equals(Role.Admin) || user.getRole().equals(Role.Trener)) {
			return repository.getAllCoachNotification(user.getId());
		} else {
			return repository.getAllAgentNotification(user.getId());
		}
		
		
	}

	public void update(Notification e) {
		repository.save(e);
	}

}
