package gov.step.app.repository;

import gov.step.app.domain.Jobapplication;
import gov.step.app.domain.enumeration.EmployeeGender;
import gov.step.app.domain.enumeration.JobApplicationStatus;
import org.elasticsearch.common.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the Jobapplication entity.
 */
public interface JobapplicationRepository extends JpaRepository<Jobapplication, Long> {

    /*@Procedure(name = "job_emp_dtl")
    void jobEmpDtl(@Param("in_location") String inLocation,@Param("in_age_frm") String inAgeFrm,@Param("in_age_to") String inAgeTo,@Param("in_applicant_name") String inApplicantName,@Param("in_gender") String inGender,@Param("in_exprience") String inExprience,@Param("in_salary_from") String inSalaryFrom,@Param("in_salary_to") String inSalaryTo,@Param("in_usercode") String inUsercode);

*/

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.jpEmployee.user.login = ?#{principal.username}")
    List<Jobapplication> findByUserIsCurrentUser();

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.jpEmployee.user.login = ?#{principal.username}")
    Page<Jobapplication> findByUserIsCurrentUser(Pageable pageable);

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.job.id = :id")
    List<Jobapplication> findByJob(@Param("id") Long id);

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.job.id = :id")
    Page<Jobapplication> findAllApplicationByJob(Pageable pageable, @Param("id") Long id);

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.job.id = :id and jobapplication.cvViewed = :viewed")
    List<Jobapplication> findByJobAndViewType(@Param("id") Long id, @Param("viewed") Boolean viewed);

    @Query("SELECT COUNT(jobapplication) from Jobapplication jobapplication where jobapplication.job.id = :id and jobapplication.cvViewed = :viewed")
    Integer findCvViewedCounter(@Param("id") Long id, @Param("viewed") Boolean viewed);

    @Query("SELECT COUNT(jobapplication) from Jobapplication jobapplication where jobapplication.job.id = :id")
    Integer findTotalApplicationByJob(@Param("id") Long id);

    @Query("SELECT COUNT(jobapplication) from Jobapplication jobapplication where jobapplication.job.id = :id and jobapplication.applicantStatus = :status")
    Integer findCounterByStatus(@Param("id") Long id, @Param("status") JobApplicationStatus status);

    @Query("SELECT jobapplication from Jobapplication jobapplication where jobapplication.job.id = :id and jobapplication.applicantStatus = :status")
    List<Jobapplication> findJobApplicationByStatus(@Param("id") Long id, @Param("status") JobApplicationStatus status);

    @Query("SELECT jobapplication from Jobapplication jobapplication where jobapplication.job.id = :id and jobapplication.jpEmployee = :status")
    List<Jobapplication> shortCvByAge(@Param("id") Long id, @Param("status") JobApplicationStatus status);
/*
    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.job.id = :jobId and jobapplication.jpEmployee.gender =:gender and (jobapplication.jpEmployee.experience between :startExp and :endExp) and (jobapplication.jpEmployee.user.dob between :startAge and :endAge) ")
    Page<Jobapplication> findSortedCv(Pageable pageable,@Param("jobId")  Long jobId ,@Param("gender") EmployeeGender gender ,@Param("startExp")  Double startExp,@Param("endExp")  Double endExp,@Param("startAge") Date startAge,@Param("endAge")  Date endAge);

    select id,to_date(DOB,'dd/mm/rrrr'), ( to_date(sysdate,'dd/mm/rrrr')-to_date(DOB,'dd/mm/rrrr'))/365 from dtedev.jp_employee
where ( to_date(sysdate,'dd/mm/rrrr')-to_date(DOB,'dd/mm/rrrr'))/365 between 25 and 30

    */

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.job.id = :jobId and jobapplication.jpEmployee.gender =:gender and ((to_date(sysdate())-to_date(jobapplication.jpEmployee.dob))/365 between :startAge and :endAge) and (jobapplication.jpEmployee.totalExperience between :startExp and :endExp) ")
    List<Jobapplication> findSortedCv(@Param("jobId")  Long jobId ,@Param("gender") EmployeeGender gender ,@Param("startExp")  Double startExp,@Param("endExp")  Double endExp , @Param("startAge")  Double startAge, @Param("endAge")  Double endAge);

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.job.id = :jobId and jobapplication.jpEmployee.gender =:gender and ((to_date(sysdate())-to_date(jobapplication.jpEmployee.dob))/365 between :startAge and :endAge) and (jobapplication.jpEmployee.totalExperience between :startExp and :endExp) and (jobapplication.jpEmployee.district.id = :distId) ")
    List<Jobapplication> findSortedCvWithDistrict(@Param("jobId")  Long jobId ,@Param("gender") EmployeeGender gender ,@Param("startExp")  Double startExp,@Param("endExp")  Double endExp , @Param("startAge")  Double startAge, @Param("endAge")  Double endAge, @Param("distId")  Long distId);

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.job.id = :jobId and ((to_date(sysdate())-to_date(jobapplication.jpEmployee.dob))/365 between :startAge and :endAge) and (jobapplication.jpEmployee.totalExperience between :startExp and :endExp) and (jobapplication.jpEmployee.district.id = :distId) ")
    List<Jobapplication> findSortedCvWithDistrictWithoutGender(@Param("jobId")  Long jobId ,@Param("startExp")  Double startExp,@Param("endExp")  Double endExp , @Param("startAge")  Double startAge, @Param("endAge")  Double endAge, @Param("distId")  Long distId);

    @Query("select jobapplication from Jobapplication jobapplication where jobapplication.job.id = :jobId and ((to_date(sysdate())-to_date(jobapplication.jpEmployee.dob))/365 between :startAge and :endAge) and (jobapplication.jpEmployee.totalExperience between :startExp and :endExp) ")
    List<Jobapplication> findSortedCvWithoutGender(@Param("jobId")  Long jobId ,@Param("startExp")  Double startExp,@Param("endExp")  Double endExp , @Param("startAge")  Double startAge, @Param("endAge")  Double endAge);


}
