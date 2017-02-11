package gov.step.app.repository;

import gov.step.app.domain.PfmsEmpMembershipForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsEmpMembershipForm entity.
 */
public interface PfmsEmpMembershipFormRepository extends JpaRepository<PfmsEmpMembershipForm,Long> {
    @Query("select modelInfo from PfmsEmpMembershipForm modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.activeStatus = true")
    List<PfmsEmpMembershipForm> getPfmsEmpMembershipFormListByEmployee(@Param("employeeInfoId") long employeeInfoId);
}
