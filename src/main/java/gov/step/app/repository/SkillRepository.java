package gov.step.app.repository;

import gov.step.app.domain.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Skill entity.
 */
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("select skill from Skill skill where skill.manager.login = ?#{principal.username}")
    List<Skill> findByManagerIsCurrentUser();

    @Query("select skill from Skill skill where skill.employee.user.login = ?#{principal.username}")
    Page<Skill> findByEmployeeUserIsCurrentUser(Pageable pageable);

    @Query("select skill from Skill skill where skill.manager.login = ?#{principal.username}")
    Page<Skill> findByManagerIsCurrentUser(Pageable pageable);

    @Query("select skill from Skill skill where skill.employee.id = :id")
    List<Skill> findByEmployee(@Param("id") Long id);
}
