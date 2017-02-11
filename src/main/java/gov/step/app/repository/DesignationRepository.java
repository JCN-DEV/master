package gov.step.app.repository;

import gov.step.app.domain.Designation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Designation entity.
 */
public interface DesignationRepository extends JpaRepository<Designation,Long> {

}
