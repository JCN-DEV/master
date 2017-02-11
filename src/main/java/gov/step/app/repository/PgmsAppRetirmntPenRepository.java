package gov.step.app.repository;

import gov.step.app.domain.PgmsAppRetirmntPen;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsAppRetirmntPen entity.
 */
public interface PgmsAppRetirmntPenRepository extends JpaRepository<PgmsAppRetirmntPen,Long> {

    @Query("select modelInfo from PgmsAppRetirmntPen modelInfo where modelInfo.aprvStatus = :statusType")
    List<PgmsAppRetirmntPen> findAllByAprvStatusOrderByIdAsc(@Param("statusType") String aprvStatus);

}
