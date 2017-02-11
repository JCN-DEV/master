package gov.step.app.repository;

import gov.step.app.domain.EduLevel;
import gov.step.app.domain.JpSkill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JpSkill entity.
 */
public interface JpSkillRepository extends JpaRepository<JpSkill,Long> {

    @Query("SELECT jpSkill from JpSkill jpSkill where lower(jpSkill.name) = :name")
    JpSkill findOneByName(@Param("name") String name);

    @Query("SELECT jpSkill from JpSkill jpSkill where jpSkill.status = :status")
    Page<JpSkill> findAllActive(Pageable pageable, @Param("status") Boolean status);

}
