package gov.step.app.repository;

import gov.step.app.domain.HrEmpServiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpServiceHistory entity.
 */
public interface HrEmpServiceHistoryRepository extends JpaRepository<HrEmpServiceHistory,Long>
{
    @Query("select modelInfo from HrEmpServiceHistory modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpServiceHistory> findAllByEmployeeIsCurrentUser();

    List<HrEmpServiceHistory> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpServiceHistory modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpServiceHistory> findAllByLogStatus(@Param("logStatus") Long logStatus);

    @Query("select modelInfo from HrEmpServiceHistory modelInfo where employeeInfo.id=:empId order by serviceDate asc")
    List<HrEmpServiceHistory> findAllEmployee(@Param("empId") Long empId);
}
