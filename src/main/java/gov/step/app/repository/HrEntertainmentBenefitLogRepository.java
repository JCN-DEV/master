package gov.step.app.repository;

import gov.step.app.domain.HrEntertainmentBenefitLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEntertainmentBenefitLog entity.
 */
public interface HrEntertainmentBenefitLogRepository extends JpaRepository<HrEntertainmentBenefitLog,Long>
{
    HrEntertainmentBenefitLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
