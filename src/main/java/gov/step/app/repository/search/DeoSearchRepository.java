package gov.step.app.repository.search;

import gov.step.app.domain.Deo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Deo entity.
 */
public interface DeoSearchRepository extends ElasticsearchRepository<Deo, Long> {
}
