package server.atena.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import server.atena.models.NoteCC;
import server.atena.models.RateCC;

public interface RateCCRepository extends CrudRepository<RateCC, Long>, JpaSpecificationExecutor<RateCC>{

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateCC r WHERE r.noteCC IS NULL AND r.typeRate = 'RATTING_'")
	Iterable<RateCC> getAllRateNoNote();
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateCC r WHERE r.noteCC IS NULL AND r.agent.id = :agentId AND r.typeRate = 'RATTING_'")
	Iterable<RateCC> getAllRateNoNoteByAgent(@Param("agentId") long id);

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateCC r WHERE r.noteCC = :note")
	List<RateCC> getAllRateCCByNoteId(@Param("note") NoteCC noteCC);
	
	@Modifying
	@Transactional
	@Query("UPDATE RateCC r SET r.noteCC.id = :newNoteId WHERE r.id = :rateId")
	void updateList(@Param("rateId") Long rateId, @Param("newNoteId") BigInteger newNoteId);
}
