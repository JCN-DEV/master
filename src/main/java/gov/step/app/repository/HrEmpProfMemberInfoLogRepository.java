package gov.step.app.repository;

import gov.step.app.domain.HrEmpProfMemberInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpProfMemberInfoLog entity.
 */
public interface HrEmpProfMemberInfoLogRepository extends JpaRepository<HrEmpProfMemberInfoLog,Long>
{
    HrEmpProfMemberInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
