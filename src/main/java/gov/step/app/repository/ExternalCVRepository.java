package gov.step.app.repository;

import gov.step.app.domain.ExternalCV;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExternalCV entity.
 */
public interface ExternalCVRepository extends JpaRepository<ExternalCV,Long> {

}
