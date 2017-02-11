package gov.step.app.repository;

import gov.step.app.domain.InstEmployee;

import gov.step.app.domain.Institute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Spring Data JPA repository for the InstEmployee entity.
 */
public interface InstEmployeeRepository extends JpaRepository<InstEmployee,Long> {

    /*@Query("select distinct instEmployee from InstEmployee instEmployee left join fetch instEmployee.payScales")
    List<InstEmployee> findAllWithEagerRelationships();

    @Query("select instEmployee from InstEmployee instEmployee left join fetch instEmployee.payScales where instEmployee.id =:id")
    InstEmployee findOneWithEagerRelationships(@Param("id") Long id);*/

    @Query("select instEmployee from InstEmployee instEmployee where lower(instEmployee.code) =lower(:code)")
    InstEmployee findOneByEmployeeCode(@Param("code") String code);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.indexNo =:indexNo")
    InstEmployee findOneByEmployeeIndexNo(@Param("indexNo") String indexNo);

   /* @Query("select max(to_number(instEmployee.indexNo)) from InstEmployee instEmployee where instEmployee.indexNo =:indexNo")
    String findMaxEmployeeIndexNo();
*/
    /*@Query("select instEmployee from InstEmployee instEmployee where lower(instEmployee.indexNo) =lower(:indexNo)")
    InstEmployee findOneByEmployeeIndexNo(@Param("indexNo") String indexNo);
*/
    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.user.login =?#{principal.username}")
    InstEmployee findCurrentOne();

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id")
    List<InstEmployee>  findOneByInstitueId(@Param("id") Long id);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id and instEmployee.mpoAppStatus =:status")
    List<InstEmployee>  findListByInstitueIdAndMpoStatus(@Param("id") Long id, @Param("status") Integer status);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id and lower(instEmployee.code) =lower(:code)")
    InstEmployee findByInstituteAndEmployeecode(@Param("id") Long id, @Param("code") String code);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id and lower(instEmployee.indexNo) =lower(:index)")
    InstEmployee findByInstituteAndEmployeeIndex(@Param("id") Long id, @Param("index") String index);

    @Query("select instEmployee from InstEmployee instEmployee where lower(instEmployee.code) =lower(:code)")
    Optional<InstEmployee> findBycode(@Param("code") String code);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.email =:email")
    Optional<InstEmployee> findByEmail(@Param("email") String email);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id AND  (instEmployee.status <= 0)  ")
    List<InstEmployee>  findPendingListByInstitueId(@Param("id") Long id);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id AND  instEmployee.status = 2 AND instEmployee.category ='Teacher' ")
    List<InstEmployee>  findApprovedListByInstitueId(@Param("id") Long id);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id AND  instEmployee.status = 2 AND instEmployee.category ='Staff' ")
    List<InstEmployee>  findApprovedStaffListByInstitueId(@Param("id") Long id);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id AND  instEmployee.status = 1 ")
    List<InstEmployee>  findDeclinedListByInstitueId(@Param("id") Long id);

    @Query("select instEmployee.institute from InstEmployee instEmployee where instEmployee.user.login = ?#{principal.username}")
    Institute findInstituteByUserIsCurrentUser();

    //employee info by code
    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.code =:code")
    InstEmployee findEmplInfoBycode(@Param("code") String code);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:id and instEmployee.isJPAdmin = true")
    List<InstEmployee>  findAllJpAdminOfInstitute(@Param("id") Long id);

    @Query("select instEmployee from InstEmployee instEmployee where instEmployee.institute.id =:instituteID")
     List<InstEmployee>  findInstituteEmpByInstitute(@Param("instituteID") Long instituteID);

}
