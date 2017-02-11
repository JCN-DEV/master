package gov.step.app.repository;

import gov.step.app.domain.CourseSub;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseSub entity.
 */
public interface CourseSubRepository extends JpaRepository<CourseSub,Long> {

}
