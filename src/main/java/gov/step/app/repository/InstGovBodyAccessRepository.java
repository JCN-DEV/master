package gov.step.app.repository;

import gov.step.app.domain.InstGovBodyAccess;

import gov.step.app.domain.Institute;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstGovBodyAccess entity.
 */
public interface InstGovBodyAccessRepository extends JpaRepository<InstGovBodyAccess,Long> {

    @Query("select instGovBodyAccess from InstGovBodyAccess instGovBodyAccess where instGovBodyAccess.institute.user.login = ?#{principal.username}")
    InstGovBodyAccess findOneByCurrentInstitute();

}
