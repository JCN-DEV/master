package gov.step.app.repository;

import gov.step.app.domain.Fee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fee entity.
 */
public interface FeeRepository extends JpaRepository<Fee,Long> {

}
