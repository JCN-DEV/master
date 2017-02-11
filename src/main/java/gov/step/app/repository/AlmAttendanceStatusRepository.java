package gov.step.app.repository;

import gov.step.app.domain.AlmAttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmAttendanceStatus entity.
 */
public interface AlmAttendanceStatusRepository extends JpaRepository<AlmAttendanceStatus,Long> {

    @Query("select modelInfo from AlmAttendanceStatus modelInfo where lower(modelInfo.attendanceStatusName) = :attendanceStatusName ")
    Optional<AlmAttendanceStatus> findOneByAttendanceStatusName(@Param("attendanceStatusName") String attendanceStatusName);

    @Query("select modelInfo from AlmAttendanceStatus modelInfo where lower(modelInfo.attendanceCode) = :attendanceCode ")
    Optional<AlmAttendanceStatus> findOneByAttendanceCode(@Param("attendanceCode") String attendanceCode);

    @Query("select modelInfo from AlmAttendanceStatus modelInfo where lower(modelInfo.shortCode) = :shortCode ")
    Optional<AlmAttendanceStatus> findOneByShortCode(@Param("shortCode") String shortCode);

    Page<AlmAttendanceStatus> findAllByActiveStatus(Pageable pageable, boolean activeStatus);
}
