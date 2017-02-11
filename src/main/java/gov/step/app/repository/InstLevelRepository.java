package gov.step.app.repository;

import gov.step.app.domain.InstCategory;
import gov.step.app.domain.InstLevel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the InstLevel entity.
 */
public interface InstLevelRepository extends JpaRepository<InstLevel,Long> {


    @Query("select instLevel from InstLevel instLevel where instLevel.code = :code")
    Optional<InstLevel> findOneByCode(@Param("code") String code);

    //return Optional
    @Query("select instLevel from InstLevel instLevel where instLevel.name = :name")
    Optional<InstLevel> findOneByName(@Param("name") String name);

    //retrun InstLevel
    @Query("select instLevel from InstLevel instLevel where instLevel.name = :name")
    InstLevel findInstLevelByName(@Param("name") String name);


    @Query("select instLevel from InstLevel instLevel where instLevel.pStatus = :val")
    Page<InstLevel> findAllInstLevlsByType(@Param("val") Boolean status, Pageable pageable);

}
