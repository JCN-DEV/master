package gov.step.app.repository;

import gov.step.app.domain.InstituteLand;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstituteLand entity.
 */
public interface InstituteLandRepository extends JpaRepository<InstituteLand,Long> {

}
