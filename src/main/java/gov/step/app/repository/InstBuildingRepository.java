package gov.step.app.repository;

import gov.step.app.domain.InstBuilding;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstBuilding entity.
 */
public interface InstBuildingRepository extends JpaRepository<InstBuilding,Long> {

}
