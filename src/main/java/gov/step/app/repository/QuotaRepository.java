package gov.step.app.repository;

import gov.step.app.domain.Quota;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Quota entity.
 */
public interface QuotaRepository extends JpaRepository<Quota,Long> {

}
