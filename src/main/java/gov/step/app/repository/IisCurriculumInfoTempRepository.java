package gov.step.app.repository;

import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.IisCurriculumInfo;
import gov.step.app.domain.IisCurriculumInfoTemp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the IisCurriculumInfoTemp entity.
 */
public interface IisCurriculumInfoTempRepository extends JpaRepository<IisCurriculumInfoTemp,Long> {

    @Query("select iisCurriculumInfoTemp from IisCurriculumInfoTemp iisCurriculumInfoTemp where iisCurriculumInfoTemp.createBy.login = ?#{principal.username}")
    List<IisCurriculumInfoTemp> findByCreateByIsCurrentUser();

    @Query("select iisCurriculumInfoTemp from IisCurriculumInfoTemp iisCurriculumInfoTemp where iisCurriculumInfoTemp.updateBy.login = ?#{principal.username}")
    List<IisCurriculumInfoTemp> findByUpdateByIsCurrentUser();


    @Query("select iisCurriculumInfo from IisCurriculumInfoTemp iisCurriculumInfo where iisCurriculumInfo.institute.id =:instituteId")
    List<IisCurriculumInfoTemp> findListByInstituteId(@Param("instituteId") Long instituteId);


    @Query("select iisCurriculumInfo from IisCurriculumInfoTemp iisCurriculumInfo where iisCurriculumInfo.institute.id = :id")
    Page<IisCurriculumInfoTemp> findAllCurriculumByUserIsCurrentUser(@Param("id") Long id, Pageable pageable);

    @Query("select iisCurriculumInfo.cmsCurriculum from IisCurriculumInfoTemp iisCurriculumInfo where iisCurriculumInfo.institute.id = :id")
    Page<CmsCurriculum> findAllCurriculumByInstituteId(@Param("id") Long id, Pageable pageable);

    @Query("select iisCurriculumInfo.cmsCurriculum from IisCurriculumInfoTemp iisCurriculumInfo where iisCurriculumInfo.institute.id = :id")
    List<CmsCurriculum> findAllCurriculumByInstituteId(@Param("id") Long id);

    @Query("select iisCurriculumInfo from IisCurriculumInfoTemp iisCurriculumInfo where iisCurriculumInfo.institute.id = :id and (iisCurriculumInfo.status is null or iisCurriculumInfo.status = 0) ")
    List<IisCurriculumInfoTemp> findAllPendingIisCurriculumByInstituteId(@Param("id") Long id);

    @Query("select iisCurriculumInfo from IisCurriculumInfoTemp iisCurriculumInfo where iisCurriculumInfo.institute.id =:instituteId and iisCurriculumInfo.status = :status")
    Page<IisCurriculumInfoTemp> findListByInstituteIdAndStatus(Pageable pageable, @Param("instituteId") Long instituteId, @Param("status") Integer status);

    @Query("select iisCurriculumInfo from IisCurriculumInfoTemp iisCurriculumInfo where iisCurriculumInfo.institute.id =:instituteId")
    IisCurriculumInfoTemp findByInstituteId(@Param("instituteId") Long instituteId);


}
