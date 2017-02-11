package gov.step.app.repository;

import gov.step.app.domain.MpoApplication;
import gov.step.app.domain.MpoApplicationStatusLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoApplicationStatusLog entity.
 */
public interface MpoApplicationStatusLogRepository extends JpaRepository<MpoApplicationStatusLog,Long> {
    @Query("select mpoApplicationStatusLog from MpoApplicationStatusLog mpoApplicationStatusLog where mpoApplicationStatusLog.mpoApplicationId.instEmployee.code = :code order by mpoApplicationStatusLog.id")
    Page<MpoApplicationStatusLog> findStatusLogByEmployeeCode(Pageable var1, @Param("code") String code);

}
