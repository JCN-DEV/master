package gov.step.app.repository;

import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.IisCourseInfo;
import gov.step.app.domain.IisCurriculumInfo;

import gov.step.app.domain.Institute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the IisCurriculumInfo entity.
 */
public interface IisCurriculumInfoRepository extends JpaRepository<IisCurriculumInfo,Long> {

    @Query("select iisCurriculumInfo from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.institute.id = :id")
    Page<IisCurriculumInfo> findAllCurriculumByUserIsCurrentUser(@Param("id") Long id, Pageable pageable);

    @Query("select iisCurriculumInfo from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.mpoEnlisted = :val")
    Page<IisCurriculumInfo> findAllMpoEnlistedCCurriculum(@Param("val") Boolean val, Pageable pageable);

    @Query("select iisCurriculumInfo.cmsCurriculum from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.institute.id = :id")
    Page<CmsCurriculum> findAllCurriculumByInstituteId(@Param("id") Long id, Pageable pageable);

    @Query("select iisCurriculumInfo.cmsCurriculum from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.institute.id = :id")
    List<CmsCurriculum> findAllCurriculumByInstituteId(@Param("id") Long id);

    @Query("select iisCurriculumInfo from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.institute.id =:instituteId")
    List<IisCurriculumInfo> findListByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select iisCurriculumInfo from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.institute.id =:instituteId and iisCurriculumInfo.status = :status")
    Page<IisCurriculumInfo> findListByInstituteIdAndStatus(Pageable pageable, @Param("instituteId") Long instituteId, @Param("status") Integer status);

    @Query("select iisCurriculumInfo from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.institute.id =:instituteId")
    IisCurriculumInfo findByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select iisCurriculumInfo from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.institute.id =:instituteId and iisCurriculumInfo.curId = :curId")
    IisCurriculumInfo findOneByInstituteIdAndCurriculumId(@Param("instituteId") Long instituteId, @Param("curId") String curId);
}
