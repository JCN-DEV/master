package gov.step.app.repository;

import gov.step.app.domain.Drugs;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Drugs entity.
 */
public interface DrugsRepository extends JpaRepository<Drugs,Long> {

}
