package gov.step.app.repository;

import gov.step.app.domain.*;

import gov.step.app.domain.enumeration.designationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the InstEmplDesignation entity.
 */
public interface InstEmplDesignationRepository extends JpaRepository<InstEmplDesignation,Long> {

    @Query("select instEmplDesignation from InstEmplDesignation instEmplDesignation ")
    List<InstEmplDesignation> findAllInstEmplDesignationList();

    @Query("select instEmplDesignation from InstEmplDesignation instEmplDesignation where instEmplDesignation.code = :code")
    InstEmplDesignation findOneByCode(@Param("code") String code);

    @Query("select instEmplDesignation from InstEmplDesignation instEmplDesignation where instEmplDesignation.name = :name")
    Optional<InstEmplDesignation> findOneByName(@Param("name") String name);

    @Query("select instEmplDesignation from InstEmplDesignation instEmplDesignation where instEmplDesignation.instLevel.id = :id and instEmplDesignation.type = gov.step.app.domain.enumeration.designationType.Teacher and instEmplDesignation.status = true")
    Page<InstEmplDesignation> findAllByInstLevelForTeacher(@Param("id") Long instLevelId, Pageable pageable);

    @Query("select instEmplDesignation from InstEmplDesignation instEmplDesignation where instEmplDesignation.instLevel.id = :id and instEmplDesignation.type = :empType and instEmplDesignation.status = true")
    Page<InstEmplDesignation> findAllByInstLevelAndType(@Param("id") Long instLevelId, @Param("empType")gov.step.app.domain.enumeration.designationType empType, Pageable pageable);

    @Query("select instEmplDesignation from InstEmplDesignation instEmplDesignation where instEmplDesignation.type = :type and instEmplDesignation.status = true")
    Page<InstEmplDesignation> findAllByDesignationTypeAndStatus(@Param("type") designationType type, Pageable pageable);

    @Query("select instEmplDesignation from InstEmplDesignation instEmplDesignation where instEmplDesignation.status = true")
    List<InstEmplDesignation> findAllActiveDesignation();

}
