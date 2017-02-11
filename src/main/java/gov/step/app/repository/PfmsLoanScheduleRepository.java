package gov.step.app.repository;

import gov.step.app.domain.PfmsLoanSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsLoanSchedule entity.
 */
public interface PfmsLoanScheduleRepository extends JpaRepository<PfmsLoanSchedule,Long> {
    @Query("select modelInfo from PfmsLoanSchedule modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.activeStatus = true")
    List<PfmsLoanSchedule> getPfmsLoanScheduleListByEmployee(@Param("employeeInfoId") long employeeInfoId);

    @Query("select modelInfo from PfmsLoanSchedule modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.pfmsLoanApp.id = :pfmsLoanAppId and modelInfo.activeStatus = true")
    List<PfmsLoanSchedule> getPfmsLoanScheduleListByEmployeeAndApp(@Param("employeeInfoId") long employeeInfoId, @Param("pfmsLoanAppId") long pfmsLoanAppId);

    @Query("select modelInfo from PfmsLoanSchedule modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.pfmsLoanApp.id = :pfmsLoanAppId and modelInfo.loanYear = :loanYear and modelInfo.loanMonth = :loanMonth and modelInfo.activeStatus = true")
    List<PfmsLoanSchedule> getPfmsLoanScheduleListByEmpAppYearMonth(@Param("employeeInfoId") long employeeInfoId, @Param("pfmsLoanAppId") long pfmsLoanAppId, @Param("loanYear") long loanYear, @Param("loanMonth") String loanMonth);
}
