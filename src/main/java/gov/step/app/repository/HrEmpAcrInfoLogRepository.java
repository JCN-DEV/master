package gov.step.app.repository;

import gov.step.app.domain.HrEmpAcrInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpAcrInfoLog entity.
 */
public interface HrEmpAcrInfoLogRepository extends JpaRepository<HrEmpAcrInfoLog,Long> {
    HrEmpAcrInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
