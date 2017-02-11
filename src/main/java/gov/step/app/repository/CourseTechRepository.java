package gov.step.app.repository;

import gov.step.app.domain.CourseTech;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseTech entity.
 */
public interface CourseTechRepository extends JpaRepository<CourseTech,Long> {

}
