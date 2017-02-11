package gov.step.app.repository;

import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.InstGenInfo;

import gov.step.app.domain.Institute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the InstGenInfo entity.
 */
public interface InstGenInfoRepository extends JpaRepository<InstGenInfo,Long> {

    InstGenInfo findByCodeIgnoreCase(String code);

    InstGenInfo findByEmailIgnoreCase(String email);

    /*@Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.status=:status1 or instGenInfo.status=:status2 order by instGenInfo.submitedDate")
    Page<InstGenInfo> findInstgenInfoByStatus(Pageable pageable,@Param("status1") Integer status1,@Param("status2") Integer status2);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.status>:status1 or instGenInfo.status = 0 order by instGenInfo.submitedDate")
    Page<InstGenInfo> findInstgenInfopendingList(Pageable pageable,@Param("status1") Integer status1)*/

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status=:status1 or instGenInfo.status=:status2 order by instGenInfo.id DESC")
    Page<InstGenInfo> findInstgenInfoByStatus(Pageable pageable,@Param("status1") Integer status1,@Param("status2") Integer status2);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status >=:status order by instGenInfo.id DESC")
    Page<InstGenInfo> findAllApprovedInstitutes(Pageable pageable,@Param("status") Integer status);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status>:status1 or instGenInfo.status = 0 or instGenInfo.status is null order by instGenInfo.id DESC")
    Page<InstGenInfo> findInstgenInfopendingList(Pageable pageable,@Param("status1") Integer status1);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status = 0 or instGenInfo.status is null order by instGenInfo.id DESC")
    Page<InstGenInfo> findInstgenInfopendingListForApproval(Pageable pageable);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status>=:status1 order by instGenInfo.id DESC")
    Page<InstGenInfo> findInstgenInfopendingInfoUpdateList(Pageable pageable,@Param("status1") Integer status1);

    // Get waiting list for approval of Institute details
    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo, Institute institute where institute.id = instGenInfo.institute.id AND institute.infoEditStatus=:status1 order by instGenInfo.id DESC")
    Page<InstGenInfo> findInstgenInfopendingInfoUpdateListByStatus(Pageable pageable,@Param("status1") String status1);

    @Query("select new InstGenInfo(instGenInfo.id,instGenInfo.code,instGenInfo.name,instGenInfo.submitedDate,instGenInfo.status) from InstGenInfo instGenInfo where instGenInfo.status = -1 order by instGenInfo.id DESC")
    Page<InstGenInfo> findInstgenInfoRejectedList(Pageable pageable);

    /*@Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.institute.id =:instituteId order by instGenInfo.submitedDate")
    InstGenInfo findByInstituteId(@Param("instituteId") Long instituteId);*/

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.institute.id =:instituteId order by instGenInfo.id DESC")
    InstGenInfo findByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.eiin =:eiin")
    Optional<InstGenInfo> findByEiin(@Param("eiin") String eiin);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.code =:code")
    Optional<InstGenInfo> findBycode(@Param("code") String code);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.institute.id =:instituteId")
    Optional<InstGenInfo> findAllGeninfoBycurrentUser(@Param("instituteId") Long instituteId);

    Optional<InstGenInfo> findOneByEmail(String email);

    @Query("select instGenInfo from InstGenInfo instGenInfo where instGenInfo.mpoCode =:mpoCode")
    InstGenInfo findOneBympoCode(@Param("mpoCode") String mpoCode);
}
