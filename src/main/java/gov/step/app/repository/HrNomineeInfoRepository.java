package gov.step.app.repository;

import gov.step.app.domain.HrNomineeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrNomineeInfo entity.
 */
public interface HrNomineeInfoRepository extends JpaRepository<HrNomineeInfo,Long>
{
    @Query("select modelInfo from HrNomineeInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrNomineeInfo> findAllByEmployeeIsCurrentUser();

    List<HrNomineeInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrNomineeInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrNomineeInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);

    /*For Add Niminee Information in PGMS Retirement Application */
    @Query("select modelInfo from HrNomineeInfo modelInfo where employeeInfo.id=:empId")
    List<HrNomineeInfo> findAllByEmployeeInfo(@Param("empId") Long EmployeeInfo);

}
