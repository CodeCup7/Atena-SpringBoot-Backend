package server.atena.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import server.atena.models.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long>{
	
    @Query("SELECT n FROM Notification n WHERE n.agent.id = :id AND n.mode = PUSH_")
    List<Notification> getAllAgentNotification(
        @Param("id") Long id
    );
	

    @Query("SELECT n FROM Notification n WHERE n.coach.id = :id AND n.mode = PUSH_")
    List<Notification> getAllCoachNotification(
        @Param("id") Long id
    );
}
