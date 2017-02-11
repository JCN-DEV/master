package gov.step.app.repository;

import gov.step.app.domain.HrEmploymentInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmploymentInfoLog entity.
 */
public interface HrEmploymentInfoLogRepository extends JpaRepository<HrEmploymentInfoLog,Long> {
    HrEmploymentInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
