package gov.step.app.repository;

import gov.step.app.domain.HrEmpAcrInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpAcrInfo entity.
 */
public interface HrEmpAcrInfoRepository extends JpaRepository<HrEmpAcrInfo,Long>
{
    @Query("select modelInfo from HrEmpAcrInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true ")
    List<HrEmpAcrInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpAcrInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpAcrInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpAcrInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);

    @Query("select modelInfo from HrEmpAcrInfo modelInfo where modelInfo.employeeInfo.id = :empInfoId")
    HrEmpAcrInfo findByEmployeeInfo(@Param("empInfoId") long employeeInfo);
}
