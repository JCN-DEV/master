package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlPayscaleAllowanceInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlPayscaleAllowanceInfo entity.
 */
public interface PrlPayscaleAllowanceInfoRepository extends JpaRepository<PrlPayscaleAllowanceInfo,Long> {

}
