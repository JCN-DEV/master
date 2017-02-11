package gov.step.app.repository;

import gov.step.app.domain.AllowanceSetup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AllowanceSetup entity.
 */
public interface AllowanceSetupRepository extends JpaRepository<AllowanceSetup,Long> {

}
