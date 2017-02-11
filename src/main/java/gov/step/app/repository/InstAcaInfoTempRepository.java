package gov.step.app.repository;

import gov.step.app.domain.InstAcaInfoTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstAcaInfoTemp entity.
 */
public interface InstAcaInfoTempRepository extends JpaRepository<InstAcaInfoTemp,Long> {

}
