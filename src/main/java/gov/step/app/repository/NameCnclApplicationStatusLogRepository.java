package gov.step.app.repository;

import gov.step.app.domain.BEdApplicationStatusLog;
import gov.step.app.domain.NameCnclApplicationStatusLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the NameCnclApplicationStatusLog entity.
 */
public interface NameCnclApplicationStatusLogRepository extends JpaRepository<NameCnclApplicationStatusLog,Long> {


    @Query("select bEdApplicationStatusLog from NameCnclApplicationStatusLog bEdApplicationStatusLog where bEdApplicationStatusLog.nameCnclApplication.instEmployee.code = :code")
    Page<NameCnclApplicationStatusLog> findStatusLogByEmployeeCode(Pageable var1, @Param("code") String code);

}
