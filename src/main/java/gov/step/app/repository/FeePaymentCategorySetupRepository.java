package gov.step.app.repository;

import gov.step.app.domain.FeePaymentCategorySetup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FeePaymentCategorySetup entity.
 */
public interface FeePaymentCategorySetupRepository extends JpaRepository<FeePaymentCategorySetup,Long> {

}
