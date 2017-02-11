package gov.step.app.repository;

import gov.step.app.domain.InstituteFinancialInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the InstituteFinancialInfo entity.
 */
public interface InstituteFinancialInfoRepository extends JpaRepository<InstituteFinancialInfo,Long> {

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status=:status1 or instGenInfo.status=:status2 order by instGenInfo.id DESC")
    Page<InstituteFinancialInfo> findInstgenInfoByStatus(Pageable pageable,@Param("status1") Integer status1,@Param("status2") Integer status2);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status>:status1 or instGenInfo.status = 0 or instGenInfo.status is null order by instGenInfo.id DESC")
    Page<InstituteFinancialInfo> findInstgenInfopendingList(Pageable pageable,@Param("status1") Integer status1);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status = 0 or instGenInfo.status is null order by instGenInfo.id DESC")
    Page<InstituteFinancialInfo> findInstgenInfopendingListForApproval(Pageable pageable);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status>=:status1 order by instGenInfo.id DESC")
    Page<InstituteFinancialInfo> findInstgenInfopendingInfoUpdateList(Pageable pageable,@Param("status1") Integer status1);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status = -1 order by instGenInfo.id DESC")
    Page<InstituteFinancialInfo> findInstgenInfoRejectedList(Pageable pageable);

    /*@Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.institute.id =:instituteId order by instGenInfo.submitedDate")
    InstGenInfo findByInstituteId(@Param("instituteId") Long instituteId);*/

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.institute.id =:instituteId order by instGenInfo.id DESC")
    InstituteFinancialInfo findByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.eiin =:eiin")
    Optional<InstituteFinancialInfo> findByEiin(@Param("eiin") String eiin);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.code =:code")
    Optional<InstituteFinancialInfo> findBycode(@Param("code") String code);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.institute.id =:instituteId")
    Optional<InstituteFinancialInfo> findAllGeninfoBycurrentUser(@Param("instituteId") Long instituteId);

    //Optional<InstituteFinancialInfo> findOneByEmail(String email);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.mpoCode =:mpoCode")
    InstituteFinancialInfo findOneBympoCode(@Param("mpoCode") String mpoCode);

}
