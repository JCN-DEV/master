/*
package gov.step.app.repository;

import gov.step.app.domain.CmsSubject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

*/
/**
 * Spring Data JPA repository for the CmsSubject entity.
 *//*

public interface CmsSubjectRepository extends JpaRepository<CmsSubject,Long> {
    @Query("select cmsSubject from CmsSubject cmsSubject where cmsSubject.cmsSyllabus.id = :cmsSyllabusId AND cmsSubject.code = :code AND cmsSubject.name= :name")
    CmsSubject findOneByNameAndSyllabusAndCode(@Param("cmsSyllabusId") Long cmsSyllabusId, @Param("code") String code, @Param("name") String name);

    @Query("select cmsSubject from CmsSubject cmsSubject where cmsSubject.cmsCurriculum.id = :id")
    Page<CmsSubject> findAllSubjectByCurriculum(Pageable pageable, @Param("id") Long id);


}


*/


package gov.step.app.repository;

import gov.step.app.domain.CmsSubject;

import gov.step.app.domain.CmsTrade;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the CmsSubject entity.
 */
public interface CmsSubjectRepository extends JpaRepository<CmsSubject,Long> {
    @Query("select cmsSubject from CmsSubject cmsSubject where cmsSubject.cmsSyllabus.id = :cmsSyllabusId AND cmsSubject.code = :code AND cmsSubject.name= :name")
    CmsSubject findOneByNameAndSyllabusAndCode(@Param("cmsSyllabusId") Long cmsSyllabusId, @Param("code") String code,  @Param("name") String name);

    @Query("select cmsSubject from CmsSubject cmsSubject where cmsSubject.cmsCurriculum.id = :cmsCurriculumId")
    List<CmsSubject> findSubjectsByCurriculum(@Param("cmsCurriculumId") Long cmsCurriculumId);

    @Query("select cmsSubject from CmsSubject cmsSubject where cmsSubject.status = 1 ")
    Page<CmsSubject> activecmsSubjects(org.springframework.data.domain.Pageable pageable);

    @Query("select cmsSubject from CmsSubject cmsSubject where cmsSubject.code = :code")
    Optional<CmsSubject> findOneByCode(@Param("code") String code);

    @Query("select cmsSubject from CmsSubject cmsSubject where cmsSubject.name = :name")
    Optional<CmsSubject> findOneByName(@Param("name") String name);

}


