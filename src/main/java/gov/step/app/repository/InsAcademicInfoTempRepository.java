package gov.step.app.repository;

import gov.step.app.domain.InsAcademicInfoTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InsAcademicInfoTemp entity.
 */
public interface InsAcademicInfoTempRepository extends JpaRepository<InsAcademicInfoTemp,Long> {

}
