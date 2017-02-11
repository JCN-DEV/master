package gov.step.app.repository;

import gov.step.app.domain.HrEmpPublicationInfoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpPublicationInfoLog entity.
 */
public interface HrEmpPublicationInfoLogRepository extends JpaRepository<HrEmpPublicationInfoLog,Long>
{
    HrEmpPublicationInfoLog findOneByIdAndLogStatus(Long id, Long logStatus);
}
