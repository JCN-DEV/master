package gov.step.app.repository;

import gov.step.app.domain.DearnessAssign;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DearnessAssign entity.
 */
public interface DearnessAssignRepository extends JpaRepository<DearnessAssign,Long> {

}
