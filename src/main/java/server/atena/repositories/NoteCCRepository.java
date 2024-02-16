package server.atena.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import server.atena.models.NoteCC;

public interface NoteCCRepository extends CrudRepository<NoteCC, Long>, JpaSpecificationExecutor<NoteCC>{
	
    
}
