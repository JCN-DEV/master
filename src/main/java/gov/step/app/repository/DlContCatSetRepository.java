package gov.step.app.repository;

import gov.step.app.domain.DlContCatSet;

import gov.step.app.domain.DlContTypeSet;
import gov.step.app.domain.DlContUpld;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DlContCatSet entity.
 */
public interface DlContCatSetRepository extends JpaRepository<DlContCatSet,Long> {

    @Query("select dlContCatSet from DlContCatSet dlContCatSet where dlContCatSet.name = :name")
    Optional<DlContCatSet> findOneByfileType(@Param("name") String name);
    @Query("select dlContCatSet from DlContCatSet dlContCatSet where dlContCatSet.code = :code")
    Optional<DlContCatSet> findOneByCode(@Param("code") String code);

    @Query("select dlContCatSet from DlContCatSet dlContCatSet where dlContCatSet.pStatus = 1 ")
    Page<DlContCatSet> activecontenttypes(org.springframework.data.domain.Pageable pageable);

}
