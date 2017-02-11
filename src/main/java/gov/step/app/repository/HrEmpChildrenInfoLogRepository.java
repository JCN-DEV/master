package gov.step.app.repository;

import gov.step.app.domain.HrEmpChildrenInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpChildrenInfoLog entity.
 */
public interface HrEmpChildrenInfoLogRepository extends JpaRepository<HrEmpChildrenInfoLog,Long>
{
    HrEmpChildrenInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
