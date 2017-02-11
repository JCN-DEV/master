package gov.step.app.repository;

import gov.step.app.domain.InstGovBodyAccessTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstGovBodyAccessTemp entity.
 */
public interface InstGovBodyAccessTempRepository extends JpaRepository<InstGovBodyAccessTemp,Long> {

}
