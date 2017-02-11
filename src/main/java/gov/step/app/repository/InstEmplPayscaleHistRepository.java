package gov.step.app.repository;

import gov.step.app.domain.InstEmplPayscaleHist;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmplPayscaleHist entity.
 */
public interface InstEmplPayscaleHistRepository extends JpaRepository<InstEmplPayscaleHist,Long> {

}
