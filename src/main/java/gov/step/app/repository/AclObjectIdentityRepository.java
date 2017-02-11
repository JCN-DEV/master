package gov.step.app.repository;

import gov.step.app.domain.AclObjectIdentity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AclObjectIdentity entity.
 */
public interface AclObjectIdentityRepository extends JpaRepository<AclObjectIdentity,Long> {

}
