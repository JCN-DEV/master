package gov.step.app.repository;

import gov.step.app.domain.InformationCorrectionStatusLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InformationCorrectionStatusLog entity.
 */
public interface InformationCorrectionStatusLogRepository extends JpaRepository<InformationCorrectionStatusLog,Long> {

    @Query("select informationCorrectionStatusLog from InformationCorrectionStatusLog informationCorrectionStatusLog where informationCorrectionStatusLog.informationCorrection.instEmployee.code = :code order by informationCorrectionStatusLog.id")
    Page<InformationCorrectionStatusLog> findStatusLogByEmployeeCode(Pageable var1, @Param("code") String code);

}
