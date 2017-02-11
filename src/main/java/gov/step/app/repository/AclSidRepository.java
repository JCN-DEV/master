package gov.step.app.repository;

import gov.step.app.domain.AclSid;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AclSid entity.
 */
public interface AclSidRepository extends JpaRepository<AclSid,Long> {

}
