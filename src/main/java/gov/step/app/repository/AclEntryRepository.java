package gov.step.app.repository;

import gov.step.app.domain.AclEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AclEntry entity.
 */
public interface AclEntryRepository extends JpaRepository<AclEntry,Long> {

}
