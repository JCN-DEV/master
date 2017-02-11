package gov.step.app.repository;

import gov.step.app.domain.PfmsEmpMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsEmpMembership entity.
 */
public interface PfmsEmpMembershipRepository extends JpaRepository<PfmsEmpMembership,Long> {
    @Query("select modelInfo from PfmsEmpMembership modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.activeStatus = true")
    List<PfmsEmpMembership> getPfmsEmpMembershipListByEmployee(@Param("employeeInfoId") long employeeInfoId);
}
