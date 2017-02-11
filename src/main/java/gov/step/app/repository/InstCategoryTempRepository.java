package gov.step.app.repository;

import gov.step.app.domain.InstCategoryTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstCategoryTemp entity.
 */
public interface InstCategoryTempRepository extends JpaRepository<InstCategoryTemp,Long> {

}
