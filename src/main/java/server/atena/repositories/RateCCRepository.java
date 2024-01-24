package server.atena.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import server.atena.models.RateCC;

public interface RateCCRepository extends CrudRepository<RateCC, Long> {
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateCC r")
	Iterable<RateCC> getAllRateNoNote();

}
