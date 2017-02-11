package gov.step.app.repository;

import gov.step.app.domain.HrEmpActingDutyInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpActingDutyInfoLog entity.
 */
public interface HrEmpActingDutyInfoLogRepository extends JpaRepository<HrEmpActingDutyInfoLog,Long>
{
    HrEmpActingDutyInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
