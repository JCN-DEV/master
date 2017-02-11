package gov.step.app.repository;

import gov.step.app.domain.EduLevel;

import gov.step.app.domain.OrganizationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EduLevel entity.
 */
public interface EduLevelRepository extends JpaRepository<EduLevel,Long> {

    public Page<EduLevel> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT eduLevel from EduLevel eduLevel where lower(eduLevel.name) = :name")
    EduLevel findOneByName(@Param("name") String name);

    @Query("SELECT eduLevel from EduLevel eduLevel where eduLevel.status = true order by id desc")
    Page<EduLevel> findAllActiveEduLevels(Pageable pageable);
}
