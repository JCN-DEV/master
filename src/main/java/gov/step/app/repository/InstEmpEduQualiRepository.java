package gov.step.app.repository;

import gov.step.app.domain.InstEmpEduQuali;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmpEduQuali entity.
 */
public interface InstEmpEduQualiRepository extends JpaRepository<InstEmpEduQuali,Long> {

    @Query("select instEmpEduQuali from InstEmpEduQuali instEmpEduQuali where instEmpEduQuali.instEmployee.id =:id")
    List<InstEmpEduQuali> findListByInstEmployeeId(@Param("id") Long id);

    @Query("select instEmpEduQuali from InstEmpEduQuali instEmpEduQuali where instEmpEduQuali.instEmployee.user.login =?#{principal.username}")
    List<InstEmpEduQuali> findAllByLogin();
    //List<InstEmpEduQuali> findByInstEmployeeId(@Param("id") Long id);

}
