package gov.step.app.repository;

import gov.step.app.domain.DlContTypeSet;

import gov.step.app.domain.DlContUpld;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DlContTypeSet entity.
 */
public interface DlContTypeSetRepository extends JpaRepository<DlContTypeSet,Long> {

    @Query("select dlContTypeSet from DlContTypeSet dlContTypeSet where dlContTypeSet.name = :name")
    Optional<DlContTypeSet> findOneByName(@Param("name") String name);

    @Query("select dlContTypeSet from DlContTypeSet dlContTypeSet where dlContTypeSet.code = :code")
    Optional<DlContTypeSet> findOneByCode(@Param("code") String code);

    @Query("select dlContTypeSet from DlContTypeSet dlContTypeSet where dlContTypeSet.pStatus = 1 ")
    Page<DlContTypeSet> activecontenttypes(org.springframework.data.domain.Pageable pageable);


}
