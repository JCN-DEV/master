package gov.step.app.repository;

import gov.step.app.domain.MpoApplication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedNativeQuery;
import java.util.List;
import java.util.Map;

/**
 * Spring Data JPA repository for the MpoApplication entity.
 */
public interface MpoApplicationRepository extends JpaRepository<MpoApplication,Long> {

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.instEmployee.code = :code")
    MpoApplication findByInstEmployeeCode(@Param("code") String code);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.instEmployee.institute.id = :id and mpoApplication.status >= :status")
    Page<MpoApplication> findApprovedListByInstituteId(Pageable var1, @Param("id") Long id, @Param("status") int status);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.instEmployee.institute.upazila.district.id = :districtId and mpoApplication.status >= :status")
    Page<MpoApplication> findApprovedListByDistrictId(Pageable var1, @Param("districtId") Long districtId, @Param("status") int status);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.instEmployee.institute.id = :id and mpoApplication.status < :status")
    Page<MpoApplication>findPendingListByInstituteId(Pageable var1, @Param("id") Long id, @Param("status") int status);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.instEmployee.institute.upazila.district.id = :districtId and mpoApplication.status = :status")
    Page<MpoApplication> findPendingListByDistrictId(Pageable var1, @Param("districtId") Long districtId, @Param("status") int status);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.status = :status")
    Page<MpoApplication> findMpoListByStatus(Pageable var1, @Param("status") int code);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.status >= :status")
    List<MpoApplication> getMpoListByStatus(@Param("status") int code);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.status >= :status and mpoApplication.id not in :ids")
    List<MpoApplication> getMpoListForCommitteeMember(@Param("status") int code, @Param("ids") List<Long> ids);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.status >= :status")
    Page<MpoApplication> findMpoListByApproveStatus(Pageable var1, @Param("status") int code);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.status < :status")
    Page<MpoApplication> findMpoPendingListForAdmin(Pageable var1, @Param("status") int code);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.status = :status and (mpoApplication.instEmployee.payScale is not null or mpoApplication.instEmployee.payScale.id>0 )")
    Page<MpoApplication> findPayScaleAssignedList(Pageable var1, @Param("status") int code);

    @Query("select mpoApplication from MpoApplication mpoApplication where mpoApplication.status = :status and mpoApplication.instEmployee.payScale is null")
    Page<MpoApplication> findPayScaleNotAssignedList(Pageable var1, @Param("status") int code);

    /*@Query(value = "select employee.* from  mpo_application mpo " +
        "    left join  inst_employee employee on (employee.id=mpo.inst_employee_id) " +
        "    where employee.pay_scale_id is not null",nativeQuery = true)

     List<Map> generateSalary();*/

}
