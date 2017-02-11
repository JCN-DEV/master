package gov.step.app.repository;

import gov.step.app.domain.PgmsAppFamilyAttach;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsAppFamilyAttach entity.
 */
public interface PgmsAppFamilyAttachRepository extends JpaRepository<PgmsAppFamilyAttach,Long>
{
    List<PgmsAppFamilyAttach> findAllByAppFamilyPenId(long appFamilyPenId);
}
