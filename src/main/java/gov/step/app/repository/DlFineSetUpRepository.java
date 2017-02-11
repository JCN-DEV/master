package gov.step.app.repository;

import gov.step.app.domain.DlFineSetUp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the DlFineSetUp entity.
 */
public interface DlFineSetUpRepository extends JpaRepository<DlFineSetUp,Long> {

    @Query("select dlFineSetUp from DlFineSetUp dlFineSetUp where dlFineSetUp.dlContSCatSet.id =:id")
    DlFineSetUp findFineInfoBySCatId(@Param("id") Long id);

}
