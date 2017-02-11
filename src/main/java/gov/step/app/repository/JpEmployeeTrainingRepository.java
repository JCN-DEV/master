package gov.step.app.repository;

import gov.step.app.domain.JpAcademicQualification;
import gov.step.app.domain.JpEmployeeTraining;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JpEmployeeTraining entity.
 */
public interface JpEmployeeTrainingRepository extends JpaRepository<JpEmployeeTraining,Long> {

    @Query("select jpEmployeeTraining from JpEmployeeTraining jpEmployeeTraining where jpEmployeeTraining.jpEmployee.id = :id")
    List<JpEmployeeTraining> findByJpEmployee(@Param("id") Long id);

}
