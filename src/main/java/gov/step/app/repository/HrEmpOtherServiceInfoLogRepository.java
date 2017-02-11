package gov.step.app.repository;

import gov.step.app.domain.HrEmpOtherServiceInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpOtherServiceInfoLog entity.
 */
public interface HrEmpOtherServiceInfoLogRepository extends JpaRepository<HrEmpOtherServiceInfoLog,Long>
{
    HrEmpOtherServiceInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
