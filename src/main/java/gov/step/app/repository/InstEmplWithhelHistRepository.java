package gov.step.app.repository;

import gov.step.app.domain.InstEmplWithhelHist;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmplWithhelHist entity.
 */
public interface InstEmplWithhelHistRepository extends JpaRepository<InstEmplWithhelHist,Long> {

}
