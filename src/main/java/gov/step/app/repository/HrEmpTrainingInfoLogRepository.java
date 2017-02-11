package gov.step.app.repository;

import gov.step.app.domain.HrEmpTrainingInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpTrainingInfoLog entity.
 */
public interface HrEmpTrainingInfoLogRepository extends JpaRepository<HrEmpTrainingInfoLog,Long>
{
    HrEmpTrainingInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
