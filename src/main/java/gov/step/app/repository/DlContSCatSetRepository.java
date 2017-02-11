package gov.step.app.repository;

import gov.step.app.domain.DlContCatSet;
import gov.step.app.domain.DlContSCatSet;

import gov.step.app.domain.DlContTypeSet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DlContSCatSet entity.
 */
public interface DlContSCatSetRepository extends JpaRepository<DlContSCatSet,Long> {
    @Query("select dlContSCatSet from DlContSCatSet dlContSCatSet where dlContSCatSet.name = :name")
    Optional<DlContCatSet> findOneByName(@Param("name") String name);
    @Query("select dlContSCatSet from DlContSCatSet dlContSCatSet where dlContSCatSet.code = :code")
    Optional<DlContCatSet> findOneByCode(@Param("code") String code);

    @Query("select dlContSCatSet from DlContSCatSet dlContSCatSet where dlContSCatSet.pStatus = 1 ")
    Page<DlContSCatSet> activeSubCategory(org.springframework.data.domain.Pageable pageable);


}
