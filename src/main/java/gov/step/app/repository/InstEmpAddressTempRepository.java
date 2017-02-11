package gov.step.app.repository;

import gov.step.app.domain.InstEmpAddressTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmpAddressTemp entity.
 */
public interface InstEmpAddressTempRepository extends JpaRepository<InstEmpAddressTemp,Long> {

}
