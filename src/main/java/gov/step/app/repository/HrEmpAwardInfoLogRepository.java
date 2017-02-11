package gov.step.app.repository;

import gov.step.app.domain.HrEmpAwardInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpAwardInfoLog entity.
 */
public interface HrEmpAwardInfoLogRepository extends JpaRepository<HrEmpAwardInfoLog,Long>
{
    HrEmpAwardInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
