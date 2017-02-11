package gov.step.app.repository;

import gov.step.app.domain.HrEmpBankAccountInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpBankAccountInfoLog entity.
 */
public interface HrEmpBankAccountInfoLogRepository extends JpaRepository<HrEmpBankAccountInfoLog,Long>
{
    HrEmpBankAccountInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
