package server.atena.repositories;

import org.springframework.data.repository.CrudRepository;

import server.atena.models.Feedback;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

}
