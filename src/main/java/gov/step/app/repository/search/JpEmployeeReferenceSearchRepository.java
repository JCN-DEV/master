package gov.step.app.repository.search;

import gov.step.app.domain.JpEmployeeReference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpEmployeeReference entity.
 */
public interface JpEmployeeReferenceSearchRepository extends ElasticsearchRepository<JpEmployeeReference, Long> {
}
