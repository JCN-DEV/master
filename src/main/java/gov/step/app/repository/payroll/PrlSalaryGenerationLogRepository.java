package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlSalaryGenerationLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlSalaryGenerationLog entity.
 */
public interface PrlSalaryGenerationLogRepository extends JpaRepository<PrlSalaryGenerationLog,Long> {

}
