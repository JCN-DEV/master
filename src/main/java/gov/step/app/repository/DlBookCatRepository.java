package gov.step.app.repository;

import gov.step.app.domain.DlBookCat;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlBookCat entity.
 */
public interface DlBookCatRepository extends JpaRepository<DlBookCat,Long> {

}
