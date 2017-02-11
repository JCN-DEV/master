package gov.step.app.repository;

import gov.step.app.domain.RisNewAppForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the RisNewAppForm entity.
 */
public interface RisNewAppFormRepository extends JpaRepository<RisNewAppForm,Long> {
    public Page<RisNewAppForm> findAllByOrderByIdDesc(org.springframework.data.domain.Pageable pageable);
    @Query("select risNewAppForm from RisNewAppForm risNewAppForm where risNewAppForm.designation=:designation ")
    Page<RisNewAppForm>findApplicantByDesignation(Pageable pageable, @Param("designation") String designation);


    @Query("select risNewAppForm from RisNewAppForm risNewAppForm  where risNewAppForm.id=:id ")
    List<RisNewAppForm>findById( @Param("id") Long id);

    @Query("select risNewAppForm from RisNewAppForm risNewAppForm  where risNewAppForm.id=:id ")
    RisNewAppForm findRisAppDetailById( @Param("id") Long id);

    @Query("select risNewAppForm from RisNewAppForm risNewAppForm  where risNewAppForm.createBy=:id ")
    Page<RisNewAppForm> findbyuserloggedin(Pageable pageable, @Param("id") Long id);



}

