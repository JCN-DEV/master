package gov.step.app.repository;

import gov.step.app.domain.HrEmployeeInfoLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmployeeInfoLog entity.
 */
public interface HrEmployeeInfoLogRepository extends JpaRepository<HrEmployeeInfoLog,Long>
{
    HrEmployeeInfoLog findOneByIdAndLogStatus(Long id,Long logStatus);
}
