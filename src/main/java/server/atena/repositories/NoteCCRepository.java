package server.atena.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import server.atena.models.NoteCC;

public interface NoteCCRepository extends CrudRepository<NoteCC, Long>, JpaSpecificationExecutor<NoteCC>{
	
    @Query("SELECT n FROM NoteCC n WHERE n.appliesDate BETWEEN :startDate AND :endDate")
    List<NoteCC> getAllNoteDates(
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );
    
    
}
