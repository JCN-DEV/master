package gov.step.app.repository;

import gov.step.app.domain.BEdApplicationStatusLog;

import gov.step.app.domain.TimeScaleApplicationStatusLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BEdApplicationStatusLog entity.
 */
public interface BEdApplicationStatusLogRepository extends JpaRepository<BEdApplicationStatusLog,Long> {


    @Query("select bEdApplicationStatusLog from BEdApplicationStatusLog bEdApplicationStatusLog where bEdApplicationStatusLog.bEdApplicationId.instEmployee.code = :code")
    Page<BEdApplicationStatusLog> findStatusLogByEmployeeCode(Pageable var1, @Param("code") String code);

}
