package gov.step.app.repository;

import gov.step.app.domain.InstLandTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstLandTemp entity.
 */
public interface InstLandTempRepository extends JpaRepository<InstLandTemp,Long> {

}
