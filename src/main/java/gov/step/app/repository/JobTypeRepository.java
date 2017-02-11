package gov.step.app.repository;

import gov.step.app.domain.EduLevel;
import gov.step.app.domain.JobType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JobType entity.
 */
public interface JobTypeRepository extends JpaRepository<JobType,Long> {

    @Query("SELECT jobType from JobType jobType where lower(jobType.name) = :name")
    JobType findOneByName(@Param("name") String name);

    @Query("SELECT jobType from JobType jobType where jobType.status = true")
    Page<JobType> allActiveJobType(Pageable pageable);
}
