package gov.step.app.repository;

import gov.step.app.domain.APScaleApplicationStatusLog;

import gov.step.app.domain.TimeScaleApplicationStatusLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the APScaleApplicationStatusLog entity.
 */
public interface APScaleApplicationStatusLogRepository extends JpaRepository<APScaleApplicationStatusLog,Long> {

    @Query("select aPScaleApplicationStatusLog from APScaleApplicationStatusLog aPScaleApplicationStatusLog where aPScaleApplicationStatusLog.apScaleApplication.instEmployee.code = :code")
    Page<APScaleApplicationStatusLog> findStatusLogByEmployeeCode(Pageable var1, @Param("code") String code);

    @Query("select aPScaleApplicationStatusLog from APScaleApplicationStatusLog aPScaleApplicationStatusLog where aPScaleApplicationStatusLog.apScaleApplication.instEmployee.code = :code")
    List<APScaleApplicationStatusLog> findStatusLogByEmployeeCode(@Param("code") String code);

}
