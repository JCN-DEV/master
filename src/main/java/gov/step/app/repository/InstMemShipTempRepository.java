package gov.step.app.repository;

import gov.step.app.domain.InstMemShipTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstMemShipTemp entity.
 */
public interface InstMemShipTempRepository extends JpaRepository<InstMemShipTemp,Long> {

}
