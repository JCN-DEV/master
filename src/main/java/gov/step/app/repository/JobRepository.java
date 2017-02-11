package gov.step.app.repository;

import gov.step.app.domain.Cat;
import gov.step.app.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the Job entity.
 */
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByApplicationDeadlineGreaterThan(Date date,Pageable pageable);

    Page<Job> findByLocationLikeAndApplicationDeadlineGreaterThan(String location, Date date,Pageable pageable);

    Page<Job> findByCatCatLikeAndApplicationDeadlineGreaterThan(String cat, LocalDate date,Pageable pageable);

    @Query("select job from Job job where job.applicationDeadline >= :date and lower(job.cat.cat) like :cat")
    List<Job> findJobsByCategoryName(@Param("date") LocalDate date,@Param("cat") String cat);

    @Query("select job from Job job where job.applicationDeadline >= :date and job.cat.id = :id")
    List<Job> findJobsByCategoryId(@Param("date") LocalDate date,@Param("id") Long id);

    @Query("select job from Job job where job.applicationDeadline >= :date and job.employer.id = :id")
    Page<Job> findJobsByEmployerId(@Param("date") LocalDate date,@Param("id") Long id, Pageable pageable);

    Page<Job> findByEmployer_NameLikeAndApplicationDeadlineGreaterThan(String name, Date date,Pageable pageable);


    @Query("select job from Job job where job.user.login = ?#{principal.username}")
    List<Job> findByUserIsCurrentUser();

   /* @Query("select distinct job from Job job left join fetch job.cats")
    List<Job> findAllWithEagerRelationships();*/

    @Query("select distinct job from Job job ")
    List<Job> findAllWithEagerRelationships();

   /* @Query("select job from Job job left join fetch job.cats where job.id =:id")
    Job findOneWithEagerRelationships(@Param("id") Long id);*/

    @Query("select job from Job job where job.id =:id")
    Job findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select job from Job job where job.user.login = ?#{principal.username}")
    Page<Job> findByEmployerUserIsCurrentUser(Pageable pageable);

    @Query("select job from Job job where job.employer.id = :id")
    List<Job> findByEmployer(@Param("id") Long id);

    @Query("select job from Job job where job.applicationDeadline >= :date")
    List<Job> findAvailableJobs(@Param("date") LocalDate date);

    @Query("select job from Job job where job.applicationDeadline >= :date")
    Page<Job> findAvailableJobsByPage(Pageable pageable,@Param("date") LocalDate date);

    @Query("select distinct job.cat from Job job where job.applicationDeadline >= :date")
    Page<Cat> findAvailableCatsByActiveJobs(Pageable pageable,@Param("date") LocalDate date);

    @Query("select job from Job job where job.applicationDeadline < :date order by job.applicationDeadline desc")
    Page<Job> findArchivedJobs(Pageable pageable,@Param("date") LocalDate date);

    @Query("select job from Job job where job.employer.institute.id =:id and job.applicationDeadline < :date order by job.applicationDeadline desc")
    Page<Job> findArchivedJobsByInstitute(Pageable pageable,@Param("id") Long id, @Param("date") LocalDate date);

    @Query("select job from Job job where job.applicationDeadline < :date and job.employer.id = :employerId")
    Page<Job> findArchivedJobsByEmployerId(@Param("date") LocalDate date,@Param("employerId") Long employerId, Pageable pageable);

    //find list of jobs for a particular employer using employer id
//    @Query("select * from (Job inner join Employer on employer.id=Job.employer_id) where employer.id=1")
//    Job findJobsByEmployerId();

}
