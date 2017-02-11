package gov.step.app.repository;

import gov.step.app.domain.ProfessorApplication;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProfessorApplication entity.
 */
public interface ProfessorApplicationRepository extends JpaRepository<ProfessorApplication,Long> {


    @Query("select professorApplication from ProfessorApplication professorApplication where professorApplication.instEmployee.code = :code")
    ProfessorApplication findByInstEmployeeCode(@org.springframework.data.repository.query.Param("code") String code);

}
