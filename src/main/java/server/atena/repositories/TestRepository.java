package server.atena.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import server.atena.models.Test;

public interface TestRepository extends CrudRepository<Test, Long> {
	
    @Query("SELECT n FROM Test n WHERE n.dateTest BETWEEN :startDate AND :endDate")
    List<Test> getAllTestsDates(
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );

}
