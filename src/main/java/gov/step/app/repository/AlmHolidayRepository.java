package gov.step.app.repository;

import gov.step.app.domain.AlmHoliday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmHoliday entity.
 */
public interface AlmHolidayRepository extends JpaRepository<AlmHoliday,Long> {
    @Query("select modelInfo from AlmHoliday modelInfo where lower(modelInfo.typeName) = :typeName ")
    Optional<AlmHoliday> findOneByTypeName(@Param("typeName") String typeName);

    Page<AlmHoliday> findAllByActiveStatus(Pageable pageable, boolean activeStatus);
}
