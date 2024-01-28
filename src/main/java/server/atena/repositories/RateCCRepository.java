package server.atena.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import server.atena.models.NoteCC;
import server.atena.models.RateCC;

public interface RateCCRepository extends CrudRepository<RateCC, Long>, JpaSpecificationExecutor<RateCC>{

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateCC r WHERE r.noteCC IS NULL")
	Iterable<RateCC> getAllRateNoNote();

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateCC r WHERE r.noteCC = :note")
	List<RateCC> getAllRateCCByNoteId(@Param("note") NoteCC noteCC);;

}
