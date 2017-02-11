package gov.step.app.repository;

import gov.step.app.domain.HrEmpPreGovtJobInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpPreGovtJobInfoLog entity.
 */
public interface HrEmpPreGovtJobInfoLogRepository extends JpaRepository<HrEmpPreGovtJobInfoLog,Long>
{
    HrEmpPreGovtJobInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
