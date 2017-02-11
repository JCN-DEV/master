package gov.step.app.repository;

import gov.step.app.domain.CourseSubject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseSubject entity.
 */
public interface CourseSubjectRepository extends JpaRepository<CourseSubject,Long> {

}
