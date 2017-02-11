package gov.step.app.repository;

import gov.step.app.domain.Job;
import gov.step.app.domain.JobPostingInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Spring Data JPA repository for the JobPostingInfo entity.
 */
public interface JobPostingInfoRepository extends JpaRepository<JobPostingInfo,Long> {

    public Page<JobPostingInfo> findAllByOrderByIdDesc(Pageable pageable);

    @Query("select category from Cat category where category.status=true order by category.id desc")
    Page<JobPostingInfo> findAllContentByCategory(Pageable pageable);

    @Query("select job from Job job where job.cat.status=true and job.cat.id in :catId order by job.id desc")
    Page<Job> findAllJobsByCat(Pageable pageable, @Param("catId") Long catId);

    /*@Query("select jobCat from Job jobCat where jobCat.cat in : catId order by jobCat.id desc")
    Page<JobPostingInfo> findAllJobs(Pageable pageable, @Param("catId") String catId);*/

}
