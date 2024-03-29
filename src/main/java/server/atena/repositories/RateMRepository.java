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
import server.atena.models.RateM;

public interface RateMRepository extends CrudRepository<RateM, Long>, JpaSpecificationExecutor<RateM>{

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateM r WHERE r.noteCC IS NULL")
	Iterable<RateM> getAllRateNoNote();
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateM r WHERE r.noteCC = :note")
	List<RateM> getAllRateMByNoteId(@Param("note") NoteCC noteCC);
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateM r WHERE r.noteCC IS NULL AND r.agent.id = :agentId")
	Iterable<RateM> getAllRateNoNoteByAgent(@Param("agentId") long id);

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("SELECT r FROM RateM r WHERE r.noteCC = :note")
	List<RateM> getAllRateCCByNoteId(@Param("note") NoteCC noteCC);
	
	@Modifying
	@Transactional
	@Query("UPDATE RateM r SET r.noteCC.id = :newNoteId WHERE r.id = :rateId")
	void updateList(@Param("rateId") Long rateId, @Param("newNoteId") BigInteger newNoteId);

}
