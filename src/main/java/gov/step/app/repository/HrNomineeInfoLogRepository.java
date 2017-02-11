package gov.step.app.repository;

import gov.step.app.domain.HrNomineeInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrNomineeInfoLog entity.
 */
public interface HrNomineeInfoLogRepository extends JpaRepository<HrNomineeInfoLog,Long>
{
    HrNomineeInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
