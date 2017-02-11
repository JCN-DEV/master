package gov.step.app.repository;

import gov.step.app.domain.HrEmpBankAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpBankAccountInfo entity.
 */
public interface HrEmpBankAccountInfoRepository extends JpaRepository<HrEmpBankAccountInfo,Long>
{

    @Query("select modelInfo from HrEmpBankAccountInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true ")
    List<HrEmpBankAccountInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpBankAccountInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpBankAccountInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpBankAccountInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);

    @Query("select modelInfo from HrEmpBankAccountInfo modelInfo where employeeInfo.id = :empInfoId")
    HrEmpBankAccountInfo findByEmployeeInfo(@Param("empInfoId") long employeeInfo);

    @Query("select modelInfo from HrEmpBankAccountInfo modelInfo where employeeInfo.id =:empInfoId AND salaryAccount=true ")
    List<HrEmpBankAccountInfo> findAllBySalaryAccountByEmployee(@Param("empInfoId") long empInfoId);

}
