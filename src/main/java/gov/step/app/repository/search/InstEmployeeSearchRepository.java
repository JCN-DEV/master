package gov.step.app.repository.search;

import gov.step.app.domain.InstEmployee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmployee entity.
 */
public interface InstEmployeeSearchRepository extends ElasticsearchRepository<InstEmployee, Long> {

}
