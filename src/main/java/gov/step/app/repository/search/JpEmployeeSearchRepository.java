package gov.step.app.repository.search;

import gov.step.app.domain.JpEmployee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpEmployee entity.
 */
public interface JpEmployeeSearchRepository extends ElasticsearchRepository<JpEmployee, Long> {
}
