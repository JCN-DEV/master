package gov.step.app.repository;

import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.CmsCurriculumRegCfg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CmsCurriculumRegCfg entity.
 */
public interface CmsCurriculumRegCfgRepository extends JpaRepository<CmsCurriculumRegCfg,Long> {


    @Query("select cmsCurriculumRegCfg from CmsCurriculumRegCfg cmsCurriculumRegCfg  where cmsCurriculumRegCfg.cmsCurriculum =:id")
    CmsCurriculumRegCfg findRegCfgByCurriculum(@Param("id") CmsCurriculum id);

    @Query("select cmsCurriculumRegCfg.cmsCurriculum from CmsCurriculumRegCfg cmsCurriculumRegCfg")
    List<CmsCurriculum> findCurriculumListFound();

}
