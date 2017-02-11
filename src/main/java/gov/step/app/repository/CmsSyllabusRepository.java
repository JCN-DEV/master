/*
package gov.step.app.repository;
import gov.step.app.domain.CmsSyllabus;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

*/
/**
 * Spring Data JPA repository for the CmsSyllabus entity.
 *//*

public interface CmsSyllabusRepository extends JpaRepository<CmsSyllabus,Long> {

    @Query("select cmsSyllabus from CmsSyllabus cmsSyllabus where cmsSyllabus.cmsCurriculum.id = :cmsCurriculumId AND cmsSyllabus.version = :version AND cmsSyllabus.name = :name")
    CmsSyllabus findOneByNameAndVersion(@Param("cmsCurriculumId") Long cmsCurriculumId,@Param("version") String version, @Param("name") String name );

}
*/


package gov.step.app.repository;
import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.CmsSyllabus;

import gov.step.app.domain.CmsTrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CmsSyllabus entity.
 */
public interface CmsSyllabusRepository extends JpaRepository<CmsSyllabus,Long> {

    @Query("select cmsSyllabus from CmsSyllabus cmsSyllabus where cmsSyllabus.cmsCurriculum.id = :cmsCurriculumId AND cmsSyllabus.version = :version AND cmsSyllabus.name = :name")
    CmsSyllabus findOneByNameAndVersion(@Param("cmsCurriculumId") Long cmsCurriculumId,@Param("version") String version, @Param("name") String name );

    @Query("select cmsSyllabus from CmsSyllabus cmsSyllabus order by cmsSyllabus.id DESC")
    Page<CmsSyllabus> findAllcmsSyllabusByOrderID(Pageable var1);

    @Query("select cmsSyllabus from CmsSyllabus cmsSyllabus where cmsSyllabus.cmsCurriculum.id = :id")
    Page<CmsSyllabus> findAllSyllabusByCurriculum(Pageable pageable, @Param("id") Long id);

}
