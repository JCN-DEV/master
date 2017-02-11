package gov.step.app.repository;

import gov.step.app.domain.FeePaymentCollection;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FeePaymentCollection entity.
 */
public interface FeePaymentCollectionRepository extends JpaRepository<FeePaymentCollection,Long> {

}
