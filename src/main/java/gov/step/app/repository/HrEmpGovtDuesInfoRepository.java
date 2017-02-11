package gov.step.app.repository;

import gov.step.app.domain.HrEmpGovtDuesInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpGovtDuesInfo entity.
 */
public interface HrEmpGovtDuesInfoRepository extends JpaRepository<HrEmpGovtDuesInfo,Long> {
    @Query("select modelInfo from HrEmpGovtDuesInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    HrEmpGovtDuesInfo findOneByEmployeeIsCurrentUser();
}
