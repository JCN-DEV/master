package gov.step.app.repository.search;

import gov.step.app.domain.InstGovernBody;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstGovernBody entity.
 */
public interface InstGovernBodySearchRepository extends ElasticsearchRepository<InstGovernBody, Long> {
}
