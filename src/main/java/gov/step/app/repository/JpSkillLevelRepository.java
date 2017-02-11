package gov.step.app.repository;

import gov.step.app.domain.EduLevel;
import gov.step.app.domain.JpSkill;
import gov.step.app.domain.JpSkillLevel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JpSkillLevel entity.
 */
public interface JpSkillLevelRepository extends JpaRepository<JpSkillLevel,Long> {

    @Query("SELECT jpSkillLevel from JpSkillLevel jpSkillLevel where lower(jpSkillLevel.name) = :name")
    JpSkillLevel findOneByName(@Param("name") String name);

    @Query("SELECT jpSkillLevel from JpSkillLevel jpSkillLevel where jpSkillLevel.status = :status")
    Page<JpSkillLevel> findAllActive(Pageable pageable, @Param("status") Boolean status);
}
