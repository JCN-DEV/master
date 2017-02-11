package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlBudgetSetupInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlBudgetSetupInfo entity.
 */
public interface PrlBudgetSetupInfoRepository extends JpaRepository<PrlBudgetSetupInfo,Long> {

}
