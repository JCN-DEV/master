package gov.step.app.repository.search;

import gov.step.app.domain.Book;
import gov.step.app.domain.Country;
import gov.step.app.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

/**
 * Spring Data ElasticSearch repository for the Job entity.
 */
public interface JobSearchRepository extends ElasticsearchRepository<Job, Long> {

    //@Query("{"bool" : {"must" : {"field" : {"name" : "?0"}}}}")
    //Page<Job> findByName(LocalDate name,Pageable pageable);

    Page<Job> findByApplicationDeadlineGreaterThan(Date date,Pageable pageable);

    Page<Job> findByLocationLikeAndApplicationDeadlineGreaterThan(String location, Date date,Pageable pageable);

    Page<Job> findByCat_CatLikeAndApplicationDeadlineGreaterThan(String cat, Date date,Pageable pageable);

    Page<Job> findByCatCatLikeAndApplicationDeadlineGreaterThan(String cat, Date date,Pageable pageable);

    Page<Job> findByEmployer_NameLikeAndApplicationDeadlineGreaterThan(String name, Date date,Pageable pageable);

   /* @Query("SELECT country from Country country where lower(country.name) = :name")
    Country findOneByName(@Param("name") String name);*/
   /* @Query("{"bool" : {"must" : {"field" : {"name" : "?0"}}}}")
    Country findOneByName(@Param("name") String name);



    "range" : {
    "timestamp" : {
        "lt" : "now"
    }
}


	{"bool" : {"must" : {"range" : {"deadline" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
	Country findOneByName(@Param("name") String name);
    */

    /*@Query("{\"bool\" : {\"must\" : {\"range\" : {\"deadline\" : {\"from\" : null,\"to\" : localdate}}}}}")
    //@Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
    Page<Job> findAvailableJob(LocalDate localDate,Pageable pageable);
*/
   /* @Query("{"bool" : {"must" : {"field" : {"name" : "?0"}}}}")
    Page<Book> findByName(String name,Pageable pageable);*/

}
