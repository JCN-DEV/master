package gov.step.app.repository;

import gov.step.app.domain.ProfessorApplicationStatusLog;

import gov.step.app.domain.TimeScaleApplicationStatusLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ProfessorApplicationStatusLog entity.
 */
public interface ProfessorApplicationStatusLogRepository extends JpaRepository<ProfessorApplicationStatusLog,Long> {


    @Query("select professorApplicationStatusLog from ProfessorApplicationStatusLog professorApplicationStatusLog where professorApplicationStatusLog.professorApplication.instEmployee.code = :code")
    Page<ProfessorApplicationStatusLog> findStatusLogByEmployeeCode(Pageable var1, @Param("code") String code);

    @Query("select professorApplicationStatusLog from ProfessorApplicationStatusLog professorApplicationStatusLog where professorApplicationStatusLog.professorApplication.instEmployee.code = :code")
    List<ProfessorApplicationStatusLog> findStatusLogByEmployeeCode(@Param("code") String code);

}
