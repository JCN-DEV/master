package gov.step.app.repository;

import gov.step.app.domain.HrEmpTransferInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpTransferInfoLog entity.
 */
public interface HrEmpTransferInfoLogRepository extends JpaRepository<HrEmpTransferInfoLog,Long>
{
    HrEmpTransferInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
