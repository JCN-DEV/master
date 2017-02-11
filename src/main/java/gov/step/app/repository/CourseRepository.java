package gov.step.app.repository;

import gov.step.app.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Course entity.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

}
