package server.atena.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import server.atena.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("select u from User u where lower(u.login) = lower(?1) ")
	User findByLogin(String login);

}