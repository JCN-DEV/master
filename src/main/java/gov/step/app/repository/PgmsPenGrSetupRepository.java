package gov.step.app.repository;

import gov.step.app.domain.PgmsPenGrSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the PgmsPenGrSetup entity.
 */
public interface PgmsPenGrSetupRepository extends JpaRepository<PgmsPenGrSetup,Long> {

    /*
    @Query("select modelInfo from PgmsPenGrSetup modelInfo where modelInfo.setupVersion = :penGrVersion")
    List<PgmsPenGrSetup> findAllBySetupVersion (@Param("penGrVersion") String setupVersion);
    */

    @Query("select modelInfo from PgmsPenGrSetup modelInfo where modelInfo.setupVersion = :penGrVersion")
    PgmsPenGrSetup findAllBySetupVersion (@Param("penGrVersion") String setupVersion);

}
