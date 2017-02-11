package gov.step.app.repository;

import gov.step.app.domain.SisQuota;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SisQuota entity.
 */
public interface SisQuotaRepository extends JpaRepository<SisQuota,Long> {

}
