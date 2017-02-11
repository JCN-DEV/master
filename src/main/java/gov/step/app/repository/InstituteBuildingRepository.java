package gov.step.app.repository;

import gov.step.app.domain.InstituteBuilding;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstituteBuilding entity.
 */
public interface InstituteBuildingRepository extends JpaRepository<InstituteBuilding,Long> {

}
