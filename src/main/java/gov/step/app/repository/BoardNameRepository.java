package gov.step.app.repository;

import gov.step.app.domain.BoardName;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BoardName entity.
 */
public interface BoardNameRepository extends JpaRepository<BoardName,Long> {

}
