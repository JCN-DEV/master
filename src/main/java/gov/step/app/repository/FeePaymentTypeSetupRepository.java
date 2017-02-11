package gov.step.app.repository;

import gov.step.app.domain.FeePaymentTypeSetup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FeePaymentTypeSetup entity.
 */
public interface FeePaymentTypeSetupRepository extends JpaRepository<FeePaymentTypeSetup,Long> {

}
