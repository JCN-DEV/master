package gov.step.app.repository;

import gov.step.app.domain.CmsTrade;
import gov.step.app.domain.IisCourseInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the IisCourseInfo entity.
 */
public interface IisCourseInfoRepository extends JpaRepository<IisCourseInfo,Long> {

    @Query("select iisCourseInfo from IisCourseInfo iisCourseInfo where iisCourseInfo.institute.id = :id")
    Page<IisCourseInfo> findAllCourseByCurrentInstitute(@Param("id") Long id, Pageable pageable);

    @Query("select iisCourseInfo from IisCourseInfo iisCourseInfo where iisCourseInfo.mpoEnlisted = :val")
    Page<IisCourseInfo> findAllMpoEnlistedCourse(@Param("val") Boolean val, Pageable pageable);

    @Query("select iisCourseInfo.cmsTrade from IisCourseInfo iisCourseInfo where iisCourseInfo.institute.id = :id")
    Page<CmsTrade> findAllTradeByInstituteId(@Param("id") Long id, Pageable pageable);

    @Query("select iisCourseInfo.cmsTrade from IisCourseInfo iisCourseInfo where iisCourseInfo.institute.id = :id")
    List<CmsTrade> findAllTradesByInstitute(@Param("id") Long id);


    @Query("select iisCourseInfo from IisCourseInfo iisCourseInfo where iisCourseInfo.institute.id =:instituteId")
    List<IisCourseInfo> findListByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select iisCourseInfo from IisCourseInfo iisCourseInfo where iisCourseInfo.institute.id =:instituteId and iisCourseInfo.cmsTrade.id =:tradeId")
    IisCourseInfo findByInstituteIdAndCmsCourseId(@Param("instituteId") Long instituteId, @Param("tradeId") Long tradeId);

    @Query("select iisCourseInfo from IisCourseInfo iisCourseInfo where iisCourseInfo.institute.id =:instituteId")
    IisCourseInfo findByInstituteId(@Param("instituteId") Long instituteId);
}
