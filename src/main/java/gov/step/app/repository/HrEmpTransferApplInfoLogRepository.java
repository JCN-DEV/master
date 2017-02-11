package gov.step.app.repository;

import gov.step.app.domain.HrEmpTransferApplInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpTransferApplInfoLog entity.
 */
public interface HrEmpTransferApplInfoLogRepository extends JpaRepository<HrEmpTransferApplInfoLog,Long>
{
    HrEmpTransferApplInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
