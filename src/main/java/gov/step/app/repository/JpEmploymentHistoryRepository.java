package gov.step.app.repository;

import gov.step.app.domain.Jobapplication;
import gov.step.app.domain.JpEmployeeExperience;
import gov.step.app.domain.JpEmploymentHistory;

import gov.step.app.domain.enumeration.JobApplicationStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the JpEmploymentHistory entity.
 */
public interface JpEmploymentHistoryRepository extends JpaRepository<JpEmploymentHistory,Long> {

    @Query("select jpEmploymentHistory from JpEmploymentHistory jpEmploymentHistory where jpEmploymentHistory.jpEmployee.id = :id")
    List<JpEmploymentHistory> findByJpEmployee(@Param("id") Long id);


    @Query("SELECT jpEmploymentHistory.startFrom from JpEmploymentHistory jpEmploymentHistory where jpEmploymentHistory.jpEmployee.id = :id and jpEmploymentHistory.startFrom = (select min(b.startFrom) " +
        "    from JpEmploymentHistory b " +
        "    where b.jpEmployee.id =:id)")
    LocalDate getFirstEmployment(@Param("id") Long id);

    @Query("SELECT jpEmploymentHistory from JpEmploymentHistory jpEmploymentHistory where jpEmploymentHistory.jpEmployee.id = :id and jpEmploymentHistory.startFrom = (select min(b.startFrom) " +
        "    from JpEmploymentHistory b " +
        "    where b.jpEmployee.id =:id)")
    JpEmploymentHistory getFirstEmploymentOfCurr(@Param("id") Long id);


}
