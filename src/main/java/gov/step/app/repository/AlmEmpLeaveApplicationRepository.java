package gov.step.app.repository;

import gov.step.app.domain.AlmEmpLeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AlmEmpLeaveApplication entity.
 */
public interface AlmEmpLeaveApplicationRepository extends JpaRepository<AlmEmpLeaveApplication,Long> {

}
