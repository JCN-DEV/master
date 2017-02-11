package gov.step.app.repository;

import gov.step.app.domain.BankSetup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BankSetup entity.
 */
public interface BankSetupRepository extends JpaRepository<BankSetup,Long> {

}
