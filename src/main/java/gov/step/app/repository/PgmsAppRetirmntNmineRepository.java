package gov.step.app.repository;

import gov.step.app.domain.PgmsAppRetirmntNmine;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsAppRetirmntNmine entity.
 */
public interface PgmsAppRetirmntNmineRepository extends JpaRepository<PgmsAppRetirmntNmine,Long> {

    List<PgmsAppRetirmntNmine> findAllByAppRetirmntPenIdOrderByIdAsc (long penId);

    @Modifying
    @Transactional
    @Query("delete from PgmsAppRetirmntNmine modelInfo where modelInfo.appRetirmntPenId = :penId")
    void deleteByAppRetirmntPenId(@Param("penId") Long appRetirmntPenId);

}
