package gov.step.app.repository;

import gov.step.app.domain.AlmEmpLeaveCancellation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AlmEmpLeaveCancellation entity.
 */
public interface AlmEmpLeaveCancellationRepository extends JpaRepository<AlmEmpLeaveCancellation,Long> {

}
