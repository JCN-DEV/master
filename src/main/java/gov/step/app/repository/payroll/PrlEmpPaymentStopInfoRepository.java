package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlEmpPaymentStopInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlEmpPaymentStopInfo entity.
 */
public interface PrlEmpPaymentStopInfoRepository extends JpaRepository<PrlEmpPaymentStopInfo,Long> {

}
