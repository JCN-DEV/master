package gov.step.app.repository;

import gov.step.app.domain.HrEmpServiceHistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpServiceHistoryLog entity.
 */
public interface HrEmpServiceHistoryLogRepository extends JpaRepository<HrEmpServiceHistoryLog,Long>
{
    HrEmpServiceHistoryLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
