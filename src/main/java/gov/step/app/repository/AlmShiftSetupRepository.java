package gov.step.app.repository;

import gov.step.app.domain.AlmShiftSetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmShiftSetup entity.
 */
public interface AlmShiftSetupRepository extends JpaRepository<AlmShiftSetup,Long> {
    @Query("select modelInfo from AlmShiftSetup modelInfo where lower(modelInfo.shiftName) = :shiftName ")
    Optional<AlmShiftSetup> findOneByShiftName(@Param("shiftName") String shiftName);

    Page<AlmShiftSetup> findAllByActiveStatus(Pageable pageable, boolean activeStatus);
}
