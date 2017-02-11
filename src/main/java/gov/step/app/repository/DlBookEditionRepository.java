package gov.step.app.repository;

import gov.step.app.domain.DlBookEdition;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the DlBookEdition entity.
 */
public interface DlBookEditionRepository extends JpaRepository<DlBookEdition,Long> {


         @Query("select dlBookEdition from DlBookEdition dlBookEdition where dlBookEdition.dlBookInfo.id = :id")
        List<DlBookEdition> getEditionListById(@Param("id") Long id);


}
