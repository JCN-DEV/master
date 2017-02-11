package gov.step.app.repository;

import gov.step.app.domain.InstEmplTraining;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmplTraining entity.
 */
public interface InstEmplTrainingRepository extends JpaRepository<InstEmplTraining,Long> {

    @Query("select instEmplTraining from InstEmplTraining instEmplTraining where instEmplTraining.instEmployee.id =:id")
    List<InstEmplTraining> findInstEmplTrainingsByEmployeeId(@Param("id") Long id);

    @Query("select instEmplTraining from InstEmplTraining instEmplTraining where instEmplTraining.instEmployee.user.login =?#{principal.username}")
    List<InstEmplTraining> findOneByCurrent();
}
