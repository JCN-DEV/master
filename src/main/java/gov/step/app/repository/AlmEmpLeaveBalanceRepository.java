package gov.step.app.repository;

import gov.step.app.domain.AlmEmpLeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the AlmEmpLeaveBalance entity.
 */
public interface AlmEmpLeaveBalanceRepository extends JpaRepository<AlmEmpLeaveBalance,Long> {
    @Query("select almEmpLeaveBalance from AlmEmpLeaveBalance almEmpLeaveBalance where almEmpLeaveBalance.employeeInfo.id = :employeeId and almEmpLeaveBalance.almLeaveType.id = :leaveTypeId")
    AlmEmpLeaveBalance findOneByAlmEmployeeInfoAndAlmLeaveType(@Param("employeeId") Long groupId, @Param("leaveTypeId") Long leaveTypeId);
}
