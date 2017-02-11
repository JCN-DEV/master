package gov.step.app.repository;

import gov.step.app.domain.CmsTrade;
import gov.step.app.domain.IisCourseInfo;
import gov.step.app.domain.IisCourseInfoTemp;

import gov.step.app.domain.IisCurriculumInfoTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the IisCourseInfoTemp entity.
 */
public interface IisCourseInfoTempRepository extends JpaRepository<IisCourseInfoTemp,Long> {

    @Query("select iisCourseInfoTemp from IisCourseInfoTemp iisCourseInfoTemp where iisCourseInfoTemp.createBy.login = ?#{principal.username}")
    List<IisCourseInfoTemp> findByCreateByIsCurrentUser();

    @Query("select iisCourseInfoTemp from IisCourseInfoTemp iisCourseInfoTemp where iisCourseInfoTemp.updateBy.login = ?#{principal.username}")
    List<IisCourseInfoTemp> findByUpdateByIsCurrentUser();

    @Query("select iisCourseInfo from IisCourseInfoTemp iisCourseInfo where iisCourseInfo.institute.id =:instituteId")
    List<IisCourseInfoTemp> findListByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select iisCourseInfo from IisCourseInfoTemp iisCourseInfo where iisCourseInfo.institute.id = :id")
    Page<IisCourseInfoTemp> findAllCourseByCurrentInstitute(@Param("id") Long id, Pageable pageable);

    @Query("select iisCourseInfo.cmsTrade from IisCourseInfoTemp iisCourseInfo where iisCourseInfo.institute.id = :id")
    Page<CmsTrade> findAllTradeByInstituteId(@Param("id") Long id, Pageable pageable);

    @Query("select iisCourseInfoTemp from IisCourseInfoTemp iisCourseInfoTemp where iisCourseInfoTemp.institute.id =:instituteId and iisCourseInfoTemp.cmsTrade.id =:tradeId")
    IisCourseInfoTemp findByInstituteIdAndCmsCourseId(@Param("instituteId") Long instituteId, @Param("tradeId") Long tradeId);

    @Query("select iisCourseInfoTemp from IisCourseInfoTemp iisCourseInfoTemp where iisCourseInfoTemp.institute.id =:instituteId")
    IisCourseInfoTemp findByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select iisCourseInfoTemp from IisCourseInfoTemp iisCourseInfoTemp where iisCourseInfoTemp.institute.id = :id and (iisCourseInfoTemp.status is null or iisCourseInfoTemp.status = 0) ")
    List<IisCourseInfoTemp> findAllPendingIisCourseByInstituteId(@Param("id") Long id);

}
