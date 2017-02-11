package gov.step.app.repository;

import gov.step.app.domain.CertName;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CertName entity.
 */
public interface CertNameRepository extends JpaRepository<CertName,Long> {

}
