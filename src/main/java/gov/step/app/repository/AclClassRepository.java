package gov.step.app.repository;

import gov.step.app.domain.AclClass;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AclClass entity.
 */
public interface AclClassRepository extends JpaRepository<AclClass,Long> {

}
