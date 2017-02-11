package gov.step.app.repository;

import gov.step.app.domain.PgmsAppRetirmntAttach;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsAppRetirmntAttach entity.
 */
public interface PgmsAppRetirmntAttachRepository extends JpaRepository<PgmsAppRetirmntAttach,Long> {

    List<PgmsAppRetirmntAttach> findAllByAppRetirmntPenId(long appRetirmntPenId);

    @Modifying
    @Transactional
    @Query("delete from PgmsAppRetirmntAttach modelInfo where modelInfo.appRetirmntPenId = :penId")
    void deleteByAppRetirmntPenId(@Param("penId") Long appRetirmntPenId);

}
