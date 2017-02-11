package gov.step.app.repository;

import gov.step.app.domain.DlBookType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlBookType entity.
 */
public interface DlBookTypeRepository extends JpaRepository<DlBookType,Long> {

}
