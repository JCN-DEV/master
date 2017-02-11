package gov.step.app.repository;

import gov.step.app.domain.Religion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Religion entity.
 */
public interface ReligionRepository extends JpaRepository<Religion,Long> {

}