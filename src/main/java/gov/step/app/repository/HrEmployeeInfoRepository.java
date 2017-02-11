package gov.step.app.repository;

import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.domain.Institute;
import gov.step.app.domain.User;
import gov.step.app.domain.enumeration.designationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrEmployeeInfo entity.
 */
public interface HrEmployeeInfoRepository extends JpaRepository<HrEmployeeInfo,Long>
{
    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.user.login = ?#{principal.username} AND modelInfo.activeAccount=true ")
    HrEmployeeInfo findOneByEmployeeUserIsCurrentUser();

    List<HrEmployeeInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmployeeInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmployeeInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);

    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.logStatus=0 OR modelInfo.logStatus=5 order by updateDate asc")
    List<HrEmployeeInfo>  findAllEmployeeByStatuses();

    @Query("select modelInfo from HrEmployeeInfo modelInfo where lower(modelInfo.nationalId) = :nationalId ")
    Optional<HrEmployeeInfo> findOneByNationalId(@Param("nationalId") String nationalId);

    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.employeeId = :employeeId")
    HrEmployeeInfo findByEmployeeId(@Param("employeeId") String employeeId);

    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.workArea.id = :workAreaId")
    List<HrEmployeeInfo> findEmployeeByWorkarea(@Param("workAreaId") long workAreaId);

    /* For PGMS Family Pension Information */
    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.nationalId = :nid ")
    HrEmployeeInfo findByNationalId(@Param("nid") String nationalId);

    HrEmployeeInfo findByEmployeeIdAndNationalId(String employeeId, String nationalId);

   // @Query("select modelInfo from HrEmployeeInfo modelInfo")
    List<HrEmployeeInfo> findAll();

    /* Asset Requisition */
    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.user = :user AND modelInfo.activeAccount=true")
    HrEmployeeInfo findEmployeeDetailsById(@Param("user") User user);

    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.designationInfo.designationInfo.designationLevel=:desigLevel")
    List<HrEmployeeInfo> findEmployeeListByDesignationLevel(@Param("desigLevel") int desigLevel);

    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.activeAccount=true AND employeeType = :employeeType ")
    List<HrEmployeeInfo> findAllEmployeeListByType(@Param("employeeType") designationType employeeType);

    // Added By Bappi Mazumder
    // get HR Data By institute_id
    @Query("select hrEmployee from HrEmployeeInfo hrEmployee where hrEmployee.institute.id = :instituteId")
    List<HrEmployeeInfo> findEmployeeListByInstitute(@Param("instituteId")Long instituteId);

    @Query("select modelInfo from HrEmployeeInfo modelInfo where modelInfo.birthDate > :birthDateMin AND modelInfo.birthDate < :birthDateMax")
    Page<HrEmployeeInfo> findEmployeeListBirthDateRange(@Param("birthDateMin") LocalDate birthDateMin, @Param("birthDateMax") LocalDate birthDateMax,Pageable pageable);

    // Update Employee for DTE Transformation
    @Modifying
    @Transactional
    @Query("update HrEmployeeInfo modelInfo set organizationType =:orgType, employeeType = :emplType, " +
        " workArea.id =:orgCatId, workAreaDtl.id =:orgId, designationInfo.id =:desigId, logId=:logId where modelInfo.id = :empId")
    void updateEmployeeForDteTransfer(@Param("empId") Long empId,
                                      @Param("emplType") designationType emplType,
                                      @Param("orgType") String orgType,
                                      @Param("orgCatId") Long orgCatId,
                                      @Param("orgId") Long orgId,
                                      @Param("desigId") Long desigId,
                                      @Param("logId") Long logId);

}
