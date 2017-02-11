package gov.step.app.repository;

import gov.step.app.domain.HrEmpAuditObjectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrEmpAuditObjectionInfo entity.
 */
public interface HrEmpAuditObjectionInfoRepository extends JpaRepository<HrEmpAuditObjectionInfo,Long> {

}
