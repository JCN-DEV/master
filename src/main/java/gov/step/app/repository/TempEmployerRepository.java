package gov.step.app.repository;

import gov.step.app.domain.Employer;
import gov.step.app.domain.TempEmployer;

import gov.step.app.domain.enumeration.TempEmployerStatus;
;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TempEmployer entity.
 */
public interface TempEmployerRepository extends JpaRepository<TempEmployer,Long> {

    @Query("select tempEmployer from TempEmployer tempEmployer where tempEmployer.user.login = ?#{principal.username}")
    List<TempEmployer> findByUserIsCurrentUser();

    @Query("select tempEmployer from TempEmployer tempEmployer where tempEmployer.manager.login = ?#{principal.username}")
    List<TempEmployer> findByManagerIsCurrentUser();

    @Query("select tempEmployer from TempEmployer tempEmployer where tempEmployer.user.id = :id and (tempEmployer.status = :status1 or tempEmployer.status = :status2 )")
    TempEmployer findOneByStatus(@Param("id") Long id, @Param("status1")TempEmployerStatus status1, @Param("status2") TempEmployerStatus status2);

    @Query("select tempEmployer from TempEmployer tempEmployer where tempEmployer.status = :status ")
    List<TempEmployer> findEmployerByStatus(@Param("status")TempEmployerStatus status);

    @Query("select tempEmployer from TempEmployer tempEmployer where tempEmployer.status = :status and tempEmployer.institute.id = :id")
    List<TempEmployer> findEmployerByInstituteAndStatus(@Param("status")TempEmployerStatus status, @Param("id") Long id);

    @Query("select tempEmployer from TempEmployer tempEmployer where (tempEmployer.status = :status1 or tempEmployer.status = :status2) and tempEmployer.institute.id = :id")
    Page<TempEmployer> findApprovedEmployerByInstituteAndStatus(Pageable pageable, @Param("status1")TempEmployerStatus status1, @Param("status2")TempEmployerStatus status2, @Param("id") Long id);

    @Query("select tempEmployer from TempEmployer tempEmployer where tempEmployer.status = :status and tempEmployer.institute.id = :id")
    Page<TempEmployer> findRejectedEmployerByInstituteAndStatus(Pageable pageable, @Param("status")TempEmployerStatus status, @Param("id") Long id);

    @Query("select tempEmployer from TempEmployer tempEmployer where lower(tempEmployer.name) = :name ")
    List<TempEmployer> findEmployerByName(@Param("name")String name);

}
