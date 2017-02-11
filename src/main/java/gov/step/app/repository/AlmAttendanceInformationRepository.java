package gov.step.app.repository;

import gov.step.app.domain.AlmAttendanceInformation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AlmAttendanceInformation entity.
 */
public interface AlmAttendanceInformationRepository extends JpaRepository<AlmAttendanceInformation,Long> {

}
