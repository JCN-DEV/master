package gov.step.app.repository;

import gov.step.app.domain.InstBuildingTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstBuildingTemp entity.
 */
public interface InstBuildingTempRepository extends JpaRepository<InstBuildingTemp,Long> {

}
