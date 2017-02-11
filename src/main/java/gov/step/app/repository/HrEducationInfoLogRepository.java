package gov.step.app.repository;

import gov.step.app.domain.HrEducationInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEducationInfoLog entity.
 */
public interface HrEducationInfoLogRepository extends JpaRepository<HrEducationInfoLog,Long>
{
    HrEducationInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
