package gov.step.app.repository;

import gov.step.app.domain.HrEmpForeignTourInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpForeignTourInfoLog entity.
 */
public interface HrEmpForeignTourInfoLogRepository extends JpaRepository<HrEmpForeignTourInfoLog,Long>
{
    HrEmpForeignTourInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
