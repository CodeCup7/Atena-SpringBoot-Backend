package server.atena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.atena.models.Notification;
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

	public Iterable<Notification> getAll(Long id) {
		return repository.getAllUserNotification(id);
	}

	public void update(Notification e) {
		repository.save(e);
	}

}
