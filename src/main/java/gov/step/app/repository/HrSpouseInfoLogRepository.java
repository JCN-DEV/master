package gov.step.app.repository;

import gov.step.app.domain.HrSpouseInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrSpouseInfoLog entity.
 */
public interface HrSpouseInfoLogRepository extends JpaRepository<HrSpouseInfoLog,Long>
{
    HrSpouseInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
