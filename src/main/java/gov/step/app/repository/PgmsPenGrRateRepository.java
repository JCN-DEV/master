package gov.step.app.repository;

import gov.step.app.domain.PgmsPenGrRate;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the PgmsPenGrRate entity.
 */
public interface PgmsPenGrRateRepository extends JpaRepository<PgmsPenGrRate,Long> {

    //@Query("select modelInfo from PgmsPenGrRate modelInfo where modelInfo.penGrSetId = :penGrSetId")
    List<PgmsPenGrRate> findAllByPenGrSetIdOrderByWorkingYearAsc (long penGrSetId);
    /*
    @Query("select modelInfo from PgmsPenGrRate modelInfo where modelInfo.penGrSetId = :penGrId and modelInfo.workingYear = :wrkYear")
    List<PgmsPenGrRate> findAllByPenGrSetIdAndWorkingYear (@Param("penGrId") long penGrSetId,@Param("wrkYear") long workingYear);
    */
    @Query("select modelInfo from PgmsPenGrRate modelInfo where modelInfo.penGrSetId = :penGrId and modelInfo.workingYear = :wrkYear")
    PgmsPenGrRate findAllByPenGrSetIdAndWorkingYear(@Param("penGrId") long penGrSetId,@Param("wrkYear") long workingYear);

}
