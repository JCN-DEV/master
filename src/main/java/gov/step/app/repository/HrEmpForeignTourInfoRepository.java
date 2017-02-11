package gov.step.app.repository;

import gov.step.app.domain.HrEmpForeignTourInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpForeignTourInfo entity.
 */
public interface HrEmpForeignTourInfoRepository extends JpaRepository<HrEmpForeignTourInfo,Long>
{
    @Query("select modelInfo from HrEmpForeignTourInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpForeignTourInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpForeignTourInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpForeignTourInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpForeignTourInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
