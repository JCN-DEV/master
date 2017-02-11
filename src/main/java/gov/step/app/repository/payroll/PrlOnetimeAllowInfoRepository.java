package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlOnetimeAllowInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlOnetimeAllowInfo entity.
 */
public interface PrlOnetimeAllowInfoRepository extends JpaRepository<PrlOnetimeAllowInfo,Long> {

}
