package server.atena.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import server.atena.models.Feedback;

public interface FeedbackRepository extends CrudRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback>{
	
    @Query("SELECT n FROM Feedback n WHERE n.dateFeedback BETWEEN :startDate AND :endDate")
    List<Feedback> getAllFeedbackDates(
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );

}
