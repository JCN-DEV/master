package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlSalaryAllowDeducInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlSalaryAllowDeducInfo entity.
 */
public interface PrlSalaryAllowDeducInfoRepository extends JpaRepository<PrlSalaryAllowDeducInfo,Long> {

}
