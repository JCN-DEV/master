package gov.step.app.repository;

import gov.step.app.domain.Deo;
import gov.step.app.domain.DeoHistLog;

import gov.step.app.domain.District;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the DeoHistLog entity.
 */
public interface DeoHistLogRepository extends JpaRepository<DeoHistLog,Long> {

    @Query("select deoHistLog from DeoHistLog deoHistLog where deoHistLog.district.id = :id")
    List<DeoHistLog> findDeosByDistrict(@Param("id") Long id);

    @Query("select deoHistLog from DeoHistLog deoHistLog where deoHistLog.district.id = :id and deoHistLog.activated = true")
    DeoHistLog findActiveLogByDistrict(@Param("id") Long id);

    @Query("select deoHistLog from DeoHistLog deoHistLog where deoHistLog.deo.id = :id and deoHistLog.activated = true")
    DeoHistLog findActiveLogByDeo(@Param("id") Long id);

    @Query("select deoHistLog.district from DeoHistLog deoHistLog where deoHistLog.deo.user.login = ?#{principal.username} and deoHistLog.activated = true")
    District findDistrictByCurrentDeo();

    @Query("select deoHistLog from DeoHistLog deoHistLog where deoHistLog.activated = true")
    List<DeoHistLog> findAllActiveDeosLog();
}
