package gov.step.app.repository;

import gov.step.app.domain.HrEmpPublicationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpPublicationInfo entity.
 */
public interface HrEmpPublicationInfoRepository extends JpaRepository<HrEmpPublicationInfo,Long>
{
    @Query("select modelInfo from HrEmpPublicationInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpPublicationInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpPublicationInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpPublicationInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpPublicationInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
