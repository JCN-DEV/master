package gov.step.app.repository;

import gov.step.app.domain.GradeSetup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GradeSetup entity.
 */
public interface GradeSetupRepository extends JpaRepository<GradeSetup,Long> {

}
