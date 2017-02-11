package gov.step.app.repository;

import gov.step.app.domain.AlmEmpLeaveTypeMap;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AlmEmpLeaveTypeMap entity.
 */
public interface AlmEmpLeaveTypeMapRepository extends JpaRepository<AlmEmpLeaveTypeMap,Long> {

}
