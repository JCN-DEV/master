package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlHouseRentAllowInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlHouseRentAllowInfo entity.
 */
public interface PrlHouseRentAllowInfoRepository extends JpaRepository<PrlHouseRentAllowInfo,Long> {

}
