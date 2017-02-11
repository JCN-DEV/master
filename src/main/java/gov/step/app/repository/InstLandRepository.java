package gov.step.app.repository;

import gov.step.app.domain.InstLand;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstLand entity.
 */
public interface InstLandRepository extends JpaRepository<InstLand,Long> {

}
