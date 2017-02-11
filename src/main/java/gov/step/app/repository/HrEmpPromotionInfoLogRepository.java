package gov.step.app.repository;

import gov.step.app.domain.HrEmpPromotionInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpPromotionInfoLog entity.
 */
public interface HrEmpPromotionInfoLogRepository extends JpaRepository<HrEmpPromotionInfoLog,Long>
{
    HrEmpPromotionInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
