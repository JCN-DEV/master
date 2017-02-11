package gov.step.app.repository;

import gov.step.app.domain.VclRequisition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VclRequisition entity.
 */
public interface VclRequisitionRepository extends JpaRepository<VclRequisition,Long> {

}
