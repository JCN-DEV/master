package gov.step.app.repository;

import gov.step.app.domain.PgmsPenGrCalculation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsPenGrCalculation entity.
 */
public interface PgmsPenGrCalculationRepository extends JpaRepository<PgmsPenGrCalculation,Long> {

}
