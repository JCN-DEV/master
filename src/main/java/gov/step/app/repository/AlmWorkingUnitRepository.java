package gov.step.app.repository;

import gov.step.app.domain.AlmWorkingUnit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AlmWorkingUnit entity.
 */
public interface AlmWorkingUnitRepository extends JpaRepository<AlmWorkingUnit,Long> {

}
