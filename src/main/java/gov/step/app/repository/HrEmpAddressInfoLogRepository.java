package gov.step.app.repository;

import gov.step.app.domain.HrEmpAddressInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpAddressInfoLog entity.
 */
public interface HrEmpAddressInfoLogRepository extends JpaRepository<HrEmpAddressInfoLog,Long>
{
    HrEmpAddressInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);

}
