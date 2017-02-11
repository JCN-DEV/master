package gov.step.app.repository;

import gov.step.app.domain.NameCnclApplication;

import gov.step.app.domain.TimeScaleApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the NameCnclApplication entity.
 */
public interface NameCnclApplicationRepository extends JpaRepository<NameCnclApplication,Long> {

    @Query("select nameCnclApplication from NameCnclApplication nameCnclApplication where nameCnclApplication.instEmployee.code = :code")
    NameCnclApplication findByInstEmployeeCode(@Param("code") String code);

    @Query("select nameCnclApplication from NameCnclApplication nameCnclApplication where nameCnclApplication.instEmployee.institute.id = :id")
    Page<NameCnclApplication> findApplicationsByInstitute(@Param("id") Long id, Pageable pageable);
}
