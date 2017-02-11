package gov.step.app.repository;

import gov.step.app.domain.FeeInvoice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FeeInvoice entity.
 */
public interface FeeInvoiceRepository extends JpaRepository<FeeInvoice,Long> {

}
