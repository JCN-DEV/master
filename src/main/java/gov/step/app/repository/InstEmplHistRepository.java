package gov.step.app.repository;

import gov.step.app.domain.InstEmplHist;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmplHist entity.
 */
public interface InstEmplHistRepository extends JpaRepository<InstEmplHist,Long> {

}
