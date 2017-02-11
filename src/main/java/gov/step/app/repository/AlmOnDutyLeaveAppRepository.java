package gov.step.app.repository;

import gov.step.app.domain.AlmOnDutyLeaveApp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AlmOnDutyLeaveApp entity.
 */
public interface AlmOnDutyLeaveAppRepository extends JpaRepository<AlmOnDutyLeaveApp,Long> {

}
