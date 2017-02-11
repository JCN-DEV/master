package gov.step.app.repository;

import gov.step.app.domain.JpExperienceCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JpExperienceCategory entity.
 */
public interface JpExperienceCategoryRepository extends JpaRepository<JpExperienceCategory,Long> {

}
