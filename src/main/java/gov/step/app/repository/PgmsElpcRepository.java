package gov.step.app.repository;

import gov.step.app.domain.PgmsElpc;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsElpc entity.
 */
public interface PgmsElpcRepository extends JpaRepository<PgmsElpc,Long> {

}
