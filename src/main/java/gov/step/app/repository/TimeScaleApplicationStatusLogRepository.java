package gov.step.app.repository;

import gov.step.app.domain.TimeScaleApplicationStatusLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeScaleApplicationStatusLog entity.
 */
public interface TimeScaleApplicationStatusLogRepository extends JpaRepository<TimeScaleApplicationStatusLog,Long> {

    @Query("select timeScaleApplicationStatusLog from TimeScaleApplicationStatusLog timeScaleApplicationStatusLog where timeScaleApplicationStatusLog.timeScaleApplicationId.instEmployee.code = :code")
    Page<TimeScaleApplicationStatusLog> findStatusLogByEmployeeCode(Pageable var1, @Param("code") String code);

}
