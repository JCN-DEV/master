package gov.step.app.repository;

import gov.step.app.domain.CareerInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the CareerInformation entity.
 */
public interface CareerInformationRepository extends JpaRepository<CareerInformation, Long> {

    @Query("select careerInformation from CareerInformation careerInformation where careerInformation.manager.login = ?#{principal.username}")
    List<CareerInformation> findByManagerIsCurrentUser();

}
