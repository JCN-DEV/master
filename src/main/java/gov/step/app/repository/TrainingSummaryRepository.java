package gov.step.app.repository;

import gov.step.app.domain.TrainingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the TrainingSummary entity.
 */
public interface TrainingSummaryRepository extends JpaRepository<TrainingSummary, Long> {

    @Query("select trainingSummary from TrainingSummary trainingSummary where trainingSummary.user.login = ?#{principal.username}")
    List<TrainingSummary> findByUserIsCurrentUser();

}
