package gov.step.app.repository;

import gov.step.app.domain.HrDepartmentalProceeding;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrDepartmentalProceeding entity.
 */
public interface HrDepartmentalProceedingRepository extends JpaRepository<HrDepartmentalProceeding,Long> {

}
