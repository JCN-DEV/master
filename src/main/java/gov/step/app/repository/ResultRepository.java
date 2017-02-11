package gov.step.app.repository;

import gov.step.app.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Result entity.
 */
public interface ResultRepository extends JpaRepository<Result, Long> {

}
