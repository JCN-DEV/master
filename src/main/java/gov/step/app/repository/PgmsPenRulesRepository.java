package gov.step.app.repository;

import gov.step.app.domain.PgmsPenRules;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsPenRules entity.
 */
public interface PgmsPenRulesRepository extends JpaRepository<PgmsPenRules,Long> {

}
