package gov.step.app.repository;

import gov.step.app.domain.PgmsAppFamilyPension;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsAppFamilyPension entity.
 */
public interface PgmsAppFamilyPensionRepository extends JpaRepository<PgmsAppFamilyPension,Long> {

    @Query("select modelInfo from PgmsAppFamilyPension modelInfo where modelInfo.aprvStatus = :statusType")
    List<PgmsAppFamilyPension> findAllByAprvStatusOrderByIdAsc(@Param("statusType") String aprvStatus);

    @Query("select modelInfo from PgmsAppFamilyPension modelInfo where modelInfo.aprvStatus = 'Approved' or modelInfo.aprvStatus = 'Reject'")
    List<PgmsAppFamilyPension> findAllOrderByIdAsc();

}
