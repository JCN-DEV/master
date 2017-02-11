package gov.step.app.repository;

import gov.step.app.domain.InstLevelTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstLevelTemp entity.
 */
public interface InstLevelTempRepository extends JpaRepository<InstLevelTemp,Long> {

}
