/*
package gov.step.app.repository;

import gov.step.app.domain.CmsCurriculum;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

*/
/**
 * Spring Data JPA repository for the CmsCurriculum entity.
 *//*

public interface CmsCurriculumRepository extends JpaRepository<CmsCurriculum,Long> {

    @Query("select cmsCurriculum from CmsCurriculum cmsCurriculum where cmsCurriculum.code = :code")
    Optional<CmsCurriculum> findOneByCode(@Param("code") String code);

    @Query("select cmsCurriculum from CmsCurriculum cmsCurriculum where cmsCurriculum.name = :name")
    Optional<CmsCurriculum> findOneByName(@Param("name") String name);


}
*/


package gov.step.app.repository;

import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.CmsSemester;

import gov.step.app.domain.DlContTypeSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

/**
 * Spring Data JPA repository for the CmsCurriculum entity.
 */
public interface CmsCurriculumRepository extends JpaRepository<CmsCurriculum,Long> {

    @Query("select cmsCurriculum from CmsCurriculum cmsCurriculum where cmsCurriculum.code = :code")
    Optional<CmsCurriculum> findOneByCode(@Param("code") String code);

    @Query("select cmsCurriculum from CmsCurriculum cmsCurriculum where cmsCurriculum.name = :name")
    Optional<CmsCurriculum> findOneByName(@Param("name") String name);

    @Query("select cmsCurriculum from CmsCurriculum cmsCurriculum where cmsCurriculum.code = :code AND cmsCurriculum.name = :name")
    CmsCurriculum findOneByCodeAndName(@Param("code") String code, @Param("name") String name);

    @Query("select cmsCurriculum from CmsCurriculum cmsCurriculum order by cmsCurriculum.id ASC")
    Page<CmsCurriculum> findAllcmsCurriculumByOrderID(Pageable var1);

    //IIS Curriculumn -> Need to change -> IisCurriculumInfo
    @Query("select iisCurriculumInfo.cmsCurriculum from IisCurriculumInfo iisCurriculumInfo where iisCurriculumInfo.institute.id =:instituteId")
    List<CmsCurriculum> findCmsCurriculumByInstituteIIS(@Param("instituteId") Long instituteId);

    @Query("select insAcademicInfo.curriculum from InsAcademicInfo insAcademicInfo where insAcademicInfo.institute.id =:instituteId")
    List<CmsCurriculum> findCmsCurriculumByInstitute(@Param("instituteId") Long instituteId);

    @Query("select cmsCurriculum from CmsCurriculum cmsCurriculum where cmsCurriculum.id not in (:ids)")
    List<CmsCurriculum> findSelectedCuriculums(@Param("ids") List<Long> ids);

    @Query("select cmsCurriculum from CmsCurriculum cmsCurriculum where cmsCurriculum.status = 1 ")
    Page<CmsCurriculum> activecmsCurriculums(org.springframework.data.domain.Pageable pageable);





}

